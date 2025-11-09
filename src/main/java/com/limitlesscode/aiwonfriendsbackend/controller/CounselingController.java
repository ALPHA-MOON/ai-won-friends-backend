package com.limitlesscode.aiwonfriendsbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limitlesscode.aiwonfriendsbackend.dto.PromptRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/counseling")
@RequiredArgsConstructor
public class CounselingController {

    private final WebClient openAiClient;
    private final ObjectMapper mapper = new ObjectMapper();

    record ChatReq(
            String model,
            List<Map<String, String>> messages,
            boolean stream) {
    }

    public record StreamChunk(
            Choice[] choices
    ) {}

    public record Choice(
            Delta delta,
            String finish_reason
    ) {}

    public record Delta(
            String content,
            String role // 옵셔널
    ) {}

    //상담 요청하기
    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> requestCounseling(@RequestBody PromptRequest request) {
        ChatReq reqBody = new ChatReq(
                "gpt-4o-mini",
                List.of(Map.of("role", "user", "content", request.prompt())),
                true
        );

        return openAiClient.post() //post 요청을 보내
                .uri("/v1/chat/completions") //chat0-gpt의 해당 주소로
                .contentType(MediaType.APPLICATION_JSON) //json 형태로 보낼거야
                .bodyValue(reqBody)// bodyValue에 들어가는 값을
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {})
                .mapNotNull(ServerSentEvent::data)
                .takeWhile(d-> !"[DONE]".equals(d))
                .flatMap(this::getContent)
                .filter(s -> s != null && !s.isEmpty())
                .map(token -> ServerSentEvent.<String>builder()
                        .data(token)
                        .build())
                .concatWith(Mono.just(ServerSentEvent.<String>builder()
                        .event("done")
                        .data("[DONE]")
                        .build()))
                .onErrorResume(e ->
                        Mono.just(ServerSentEvent.<String>builder()
                                .event("error")
                                .data("stream_error")
                                .build()));

    }

    private Flux<String> getContent(String dataLine) {
        try {
            StreamChunk chunk = mapper.readValue(dataLine, StreamChunk.class);
            if (chunk.choices() == null) return Flux.empty();

            return Flux.fromArray(chunk.choices())
                    .map(Choice::delta)
                    .filter(Objects::nonNull)
                    .map(Delta::content)
                    .filter(Objects::nonNull);
        } catch (Exception e) {
            // JSON이 아닌 하트비트/코멘트 라인 등은 무시
            return Flux.empty();
        }
    }

    //기존 상담내용 조회하기
    @PostMapping("/{id}")
    public String getCounseling(@PathVariable String id) {
        return "getCounseling id: " + id ;
    }

    //상담목록 조회하기
    @GetMapping()
    public String getCounselingList() {
        return "getCounselingList";
    }

    //상담 내용 삭제하기
    @DeleteMapping("/{id}")
    public String deleteCounseling(@PathVariable String id) {
        return "deleteCounseling id: " + id;
    }
}

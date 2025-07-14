package br.com.alura.codechella;

import br.com.alura.codechella.DTO.EventoDTO;
import br.com.alura.codechella.Enums.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodechellaApplicationTests {

	@Autowired
	private WebTestClient webTestClient;



	@Test
	void cadastraNovoEvento() {
		EventoDTO eventoDTO = new EventoDTO(null, TipoEvento.SHOW, "Kiss",
				LocalDate.parse("2025-01-01"),"Show da melhor banda do mundo");

		webTestClient.post().uri("/eventos").bodyValue(eventoDTO).exchange()
				.expectStatus().isCreated()
				.expectBody(EventoDTO.class)
				.value(response -> {
					assertNotNull(response.id());
					assertEquals(eventoDTO.tipoEvento(),response.tipoEvento());
					assertEquals(eventoDTO.nome(),response.nome());
					assertEquals(eventoDTO.data(),response.data());
					assertEquals(eventoDTO.descricao(),response.descricao());
				});
	}

	@Test
	void buscarEvento() {
		EventoDTO eventoDTO = new EventoDTO(13L, TipoEvento.SHOW, "The Weeknd",
				LocalDate.parse("2025-11-02"),"Um show eletrizante ao ar livre com muitos efeitos especiais.");

		webTestClient.get().uri("/eventos").exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(EventoDTO.class)
				.value(response -> {
					EventoDTO eventoResponse = response.get(12);
					assertEquals(eventoDTO.id(),eventoResponse.id());
					assertEquals(eventoDTO.tipoEvento(),eventoResponse.tipoEvento());
					assertEquals(eventoDTO.nome(),eventoResponse.nome());
					assertEquals(eventoDTO.data(),eventoResponse.data());
					assertEquals(eventoDTO.descricao(),eventoResponse.descricao());
				});
	}

	@Test
	void updateEvento() {
		EventoDTO eventoDTO = new EventoDTO(13L, TipoEvento.SHOW, "AC/DC",
				LocalDate.parse("2025-01-04"),"Show da melhor banda do mundo");

		Long id = eventoDTO.id();
		webTestClient.put().uri("/eventos/"+ id).bodyValue(eventoDTO).exchange()
				.expectStatus().is2xxSuccessful().expectBody(EventoDTO.class).value(
						response -> {
							assertEquals(eventoDTO.id(),response.id());
							assertEquals(eventoDTO.tipoEvento(),response.tipoEvento());
							assertEquals(eventoDTO.nome(),response.nome());
							assertEquals(eventoDTO.data(),response.data());
							assertEquals(eventoDTO.descricao(),response.descricao());

						}
				);
	}

	@Test
	void deleteEvento() {
		EventoDTO eventoDTOPost = new EventoDTO(null, TipoEvento.SHOW, "AC/DC",
				LocalDate.parse("2025-01-04"),"Show da melhor banda do mundo");

		EventoDTO eventoCriado = webTestClient.post().uri("/eventos").bodyValue(eventoDTOPost).exchange()
				.expectStatus().isCreated()
				.expectBody(EventoDTO.class)
						.returnResult().getResponseBody();

		assertNotNull(eventoCriado);
		Long id = eventoCriado.id();

		webTestClient.delete().uri("/eventos/" + id).exchange().expectStatus().isOk();

		webTestClient.get().uri("/eventos/" + id).exchange().expectStatus().isNotFound();



	}




}

package br.com.robertosouza.desafio;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.robertosouza.desafio.controllers.PessoaController;
import br.com.robertosouza.desafio.model.Pessoa;
import br.com.robertosouza.desafio.services.PessoaService;

@RunWith(SpringRunner.class)
@WebMvcTest(PessoaController.class)
public class PessoaRepositoryTests {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PessoaService pessoaService;
	
	@Test
	public void PessoaListTest() throws Exception {
		Pessoa p1 = new Pessoa(1L, "Roberto Souza", LocalDate.now().minusYears(21), "027.852.070-76", true, new HashSet<>());
		Pessoa p2 = new Pessoa(2L, "Paulo Souza", LocalDate.now().minusYears(40), "823.836.138-94", false, new HashSet<>());
		
		List<Pessoa> pessoas = Arrays.asList(p1, p2);
		given(this.pessoaService.findAll()).willReturn(pessoas);
		
		this.mvc.perform(get("/pessoa")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
	@Test
	public void PessoaGetTest() throws Exception {
		Pessoa p1 = new Pessoa(1L, "Roberto Souza", LocalDate.now().minusYears(21), "027.852.070-76", true, new HashSet<>());
		
		given(this.pessoaService.get(1L)).willReturn(p1);
		
		this.mvc.perform(get("/pessoa/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		
	}
	
	@Test
	public void PessoaInsertTest() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Roberto Souza");
		pessoa.setCpf("027.852.070-76");
		pessoa.setDataNascimento(LocalDate.parse("1996-10-29", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		pessoa.setFuncionario(true);
		pessoa.setProjetos(new HashSet<>());
		
		Pessoa p1 = new Pessoa(1L, "Roberto Souza", LocalDate.parse("1996-10-29", DateTimeFormatter.ofPattern("yyyy-MM-dd")), "027.852.070-76", true, new HashSet<>());
		
		given(this.pessoaService.insert(pessoa)).willReturn(p1);
		
		this.mvc.perform(
				post("/pessoa")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(pessoa))
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		
		verify(this.pessoaService, times(1)).get(1L);
	}
	
	@Test
	public void PessoaDeleteTest() throws Exception {
		Pessoa p1 = new Pessoa(1L, "Roberto Souza", LocalDate.now().minusYears(21), "027.852.070-76", true, new HashSet<>());
		
		verify(this.pessoaService, never()).delete(p1.getId());
		
		this.mvc.perform(delete("/pessoa/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		
	}
	
	 public static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	
}

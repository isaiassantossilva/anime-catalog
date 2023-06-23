package com.santos.animecatalog.infra.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.santos.animecatalog.AnimeServer
import com.santos.animecatalog.application.usecase.CreateAnimeInput
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@Suppress("SpellCheckingInspection", "NonAsciiCharacters")
@SpringBootTest(classes = [AnimeServer::class])
@AutoConfigureMockMvc
class AnimeControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun `Deve criar um anime pela api`() {
        val name = "Dragon Ball Z Dublado"
        val description = "Goku está mais velho e se casou com Chichi..."
        val imageUrl = "http://url-storage.png"
        val body = CreateAnimeInput(name, description, imageUrl)
        this.mockMvc.post("/anime") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(body)
        }.andExpect {
            status { isCreated() }
            content { jsonPath("$.name", IsEqual.equalTo(name)) }
        }
    }

    @Test
    fun `Não deve criar um anime com campos vazios`() {
        val name = ""
        val description = "Goku está mais velho e se casou com Chichi..."
        val imageUrl = ""
        val body = CreateAnimeInput(name, description, imageUrl)
        this.mockMvc.post("/anime") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(body)
        }.andExpect {
            status { isBadRequest() }
            content { json(
                """
                    {
                    	"message": "Failed to create Anime",
                    	"errors": [
                    		{
                    			"message": "Name can not be empty"
                    		},
                    		{
                    			"message": "ImageUrl can not be empty"
                    		}
                    	]
                    }
                """.trimIndent()
            ) }
        }
    }
}
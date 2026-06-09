package com.ironhack.sanctuary.service;

import com.ironhack.sanctuary.model.Animal;
import com.ironhack.sanctuary.repository.AnimalRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SanctuaryAiService {

    private final ChatClient chatClientWithMemory;
    private final AnimalRepository animalRepository;

// Inyecta el AnimalRepository en el constructor.
    public SanctuaryAiService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, AnimalRepository animalRepository) {
        this.chatClientWithMemory = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        this.animalRepository = animalRepository;
    }

    public String getCareAdvice(String conversationId, Long animalId) {
// 1. Busca el animal real en la base de datos
        Optional<Animal> animalOpt = animalRepository.findById(animalId);

// Si no existe, corta la ejecución y devuelve un error sin gastar tokens de la IA.
        if (animalOpt.isEmpty()) {
            return "Error: No se ha encontrado ningún animal con el ID " + animalId + " en la base de datos del santuario.";
        }

// 2. Extrae los datos reales.
        Animal animal = animalOpt.get();
        String animalName = animal.getName();
        String healthStatus = animal.getHealthStatus().name();

// 3. Construye el prompt dinámico.
        String prompt = "Tengo un animal llamado " + animalName + " cuyo estado actual es: " + healthStatus + ". ¿Qué cuidados inmediatos me recomiendas?";

// 4. Llama a la IA.
        return chatClientWithMemory
                .prompt(prompt)
                .system("Eres el veterinario jefe de Pura Vida Animal Sanctuary. Da respuestas profesionales, compasivas y muy concisas (máximo 3 líneas) sobre cómo cuidar animales de granja rescatados.")
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
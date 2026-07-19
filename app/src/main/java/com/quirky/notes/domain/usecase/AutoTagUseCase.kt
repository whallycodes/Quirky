package com.quirky.notes.domain.usecase

import com.quirky.notes.domain.model.Note
import com.quirky.notes.domain.repository.NoteRepository
import java.time.Instant
import javax.inject.Inject

class AutoTagUseCase @Inject constructor() {
    /**
     * Automatically assigns tags to a note based on keyword matching.
     * This is a simple implementation. Can be upgraded to ML Kit or TensorFlow Lite.
     */
    fun autoTag(content: String): List<String> {
        val tags = mutableListOf<String>()
        val lowerContent = content.lowercase()

        // Simple keyword-based tagging
        val tagKeywords = mapOf(
            "shopping" to listOf("buy", "shopping", "store", "store", "milk", "bread"),
            "recipe" to listOf("recipe", "cook", "bake", "ingredient", "cup", "tablespoon"),
            "work" to listOf("meeting", "deadline", "project", "team", "client", "proposal"),
            "idea" to listOf("idea", "thought", "concept", "brainstorm", "inspiration"),
            "personal" to listOf("feeling", "mood", "emotion", "personal"),
            "reminder" to listOf("remember", "don't forget", "must", "important")
        )

        for ((tag, keywords) in tagKeywords) {
            if (keywords.any { lowerContent.contains(it) }) {
                tags.add(tag)
            }
        }

        return tags.distinct()
    }
}

package com.openclassrooms.notes.model.data.service

import android.annotation.SuppressLint
import com.openclassrooms.notes.model.data.Note

/**
 * Implementation of the [NotesApiService] interface that stores note in local
 */
class LocalNotesApiService : NotesApiService {

    override fun addNote(note: Note) {
        val listOfNotes = mutableListOf<Note>()
        listOfNotes.add(note)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getAllNotes(): List<Note> {

        val listOfNotes = mutableListOf<Note>()
            listOfNotes.add(Note("titre 1", "corps 1"))
            listOfNotes.add(Note("titre 2", "corps 2"))
            listOfNotes.add(Note("Suivez vos rêves", "Ne laissez rien vous arrêter de suivre vos rêves. Travaillez dur et ne vous découragez jamais. Vos rêves sont à votre portée, alors n'ayez pas peur de les poursuivre."))
            listOfNotes.add(Note("Soyez gentil avec les autres", "Le monde a besoin de plus de gentillesse. Soyez gentil avec les autres, même si ce n'est pas facile. La gentillesse peut faire une grande différence dans le monde."))
            listOfNotes.add(Note("Aidez les autres", "Le monde est un meilleur endroit lorsque nous travaillons ensemble. Aidez les autres, même si c'est juste un petit geste. Chaque geste compte."))
            listOfNotes.add(Note("Soyez reconnaissant pour ce que vous avez.", "Il y a toujours quelqu'un qui a moins que vous. Soyez reconnaissant pour ce que vous avez, même si ce n'est pas grand-chose. La gratitude peut vous rendre heureux et épanoui."))
            listOfNotes.add(Note("Vivez le moment présent", "Ne vous inquiétez pas du passé et ne vous inquiétez pas de l'avenir. Vivez le moment présent et profitez de chaque minute. Le moment présent est tout ce que vous avez"))
            listOfNotes.add(Note("Prenez soin de vous", "Mangez sainement, faites de l'exercice et dormez suffisamment. Prenez soin de votre corps et de votre esprit. Vous êtes votre meilleur atout."))
            listOfNotes.add(Note("Passez du temps avec vos proches", "Ils sont les plus importants dans votre vie. Passez du temps avec vos proches et montrez-leur à quel point vous les aimez. Ils sont votre famille et vos amis."))
            listOfNotes.add(Note("Riez et amusez-vous.", "La vie est trop courte pour être sérieuse tout le temps. Riez et amusez-vous. Passez du temps à faire les choses que vous aimez."))
        return listOfNotes
    }
}
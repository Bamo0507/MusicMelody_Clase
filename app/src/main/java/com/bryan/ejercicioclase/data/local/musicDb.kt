package com.bryan.ejercicioclase.data.local

data class ArtistDTO(
    val id: String,
    val name: String,
    val monthlyListeners: Int,
    val album_count: Int
)

data class SongDTO(
    val id: Int,
    val name: String,
    val artist_id: String,
    val genre: String,
    val duration: Int
)

object MusicApi {
    fun getArtists(): List<ArtistDTO> = listOf(
        ArtistDTO("A", "Metallica", 8_234_567, 10),
        ArtistDTO("B", "Gojira", 1_234_567, 6),
        ArtistDTO("C", "Taylor Swift", 9_876_543, 9),
        ArtistDTO("D", "Ed Sheeran", 7_654_321, 5),
        ArtistDTO("E", "Lady Gaga", 6_543_210, 7),
        ArtistDTO("F", "Bad Bunny", 9_123_456, 4),
        ArtistDTO("G", "Wisin", 3_456_789, 8),
        ArtistDTO("H", "Feid", 4_567_890, 3),
        ArtistDTO("I", "Eminem", 8_901_234, 11),
        ArtistDTO("J", "Daft Punk", 5_678_901, 4),
        ArtistDTO("K", "Calvin Harris", 6_789_012, 5)
    )

    fun getSongs(): List<SongDTO> {
        var songId = 1
        return listOf(
            // Metallica
            SongDTO(songId++, "Enter Sandman", "A", "Heavy Metal", 332),
            SongDTO(songId++, "Nothing Else Matters", "A", "Heavy Metal", 386),
            SongDTO(songId++, "Master of Puppets", "A", "Thrash Metal", 515),
            SongDTO(songId++, "One", "A", "Heavy Metal", 447),
            SongDTO(songId++, "The Unforgiven", "A", "Heavy Metal", 387),
            
            // Gojira
            SongDTO(songId++, "Stranded", "B", "Progressive Metal", 311),
            SongDTO(songId++, "L'Enfant Sauvage", "B", "Progressive Metal", 246),
            SongDTO(songId++, "Flying Whales", "B", "Progressive Metal", 461),
            SongDTO(songId++, "Silvera", "B", "Progressive Metal", 208),
            SongDTO(songId++, "The Heaviest Matter of the Universe", "B", "Death Metal", 237),
            
            // Taylor Swift
            SongDTO(songId++, "Shake It Off", "C", "Pop", 219),
            SongDTO(songId++, "Blank Space", "C", "Pop", 231),
            SongDTO(songId++, "Love Story", "C", "Country Pop", 235),
            SongDTO(songId++, "You Belong with Me", "C", "Country Pop", 231),
            SongDTO(songId++, "Cardigan", "C", "Indie Folk", 239),
            
            // Ed Sheeran
            SongDTO(songId++, "Shape of You", "D", "Pop", 233),
            SongDTO(songId++, "Perfect", "D", "Pop", 263),
            SongDTO(songId++, "Thinking Out Loud", "D", "Pop", 281),
            SongDTO(songId++, "Castle on the Hill", "D", "Pop Rock", 261),
            SongDTO(songId++, "Photograph", "D", "Pop", 258),
            
            // Lady Gaga
            SongDTO(songId++, "Bad Romance", "E", "Pop", 294),
            SongDTO(songId++, "Poker Face", "E", "Pop", 237),
            SongDTO(songId++, "Just Dance", "E", "Electropop", 242),
            SongDTO(songId++, "Shallow", "E", "Country Rock", 215),
            SongDTO(songId++, "Born This Way", "E", "Electropop", 260),
            
            // Bad Bunny
            SongDTO(songId++, "Callaita", "F", "Reggaeton", 251),
            SongDTO(songId++, "Yo Perreo Sola", "F", "Reggaeton", 172),
            SongDTO(songId++, "Safaera", "F", "Reggaeton", 295),
            SongDTO(songId++, "La Canción", "F", "Reggaeton", 242),
            SongDTO(songId++, "Dakiti", "F", "Reggaeton", 205),
            
            // Wisin
            SongDTO(songId++, "Adrenalina", "G", "Reggaeton", 226),
            SongDTO(songId++, "Escapate Conmigo", "G", "Reggaeton", 232),
            SongDTO(songId++, "Vacaciones", "G", "Reggaeton", 225),
            SongDTO(songId++, "Que Viva la Vida", "G", "Reggaeton", 253),
            SongDTO(songId++, "Amor", "G", "Latin Pop", 217),
            
            // Feid
            SongDTO(songId++, "Chimbita", "H", "Reggaeton", 184),
            SongDTO(songId++, "Porfa", "H", "Reggaeton", 217),
            SongDTO(songId++, "Fresh Kerias", "H", "Reggaeton", 196),
            SongDTO(songId++, "FUMETEO", "H", "Reggaeton", 200),
            SongDTO(songId++, "Ferxxo 100", "H", "Reggaeton", 188),
            
            // Eminem
            SongDTO(songId++, "Lose Yourself", "I", "Hip Hop", 326),
            SongDTO(songId++, "The Real Slim Shady", "I", "Hip Hop", 284),
            SongDTO(songId++, "Without Me", "I", "Hip Hop", 290),
            SongDTO(songId++, "Stan", "I", "Hip Hop", 444),
            SongDTO(songId++, "Love The Way You Lie", "I", "Hip Hop", 263),
            
            // Daft Punk
            SongDTO(songId++, "Get Lucky", "J", "Disco", 248),
            SongDTO(songId++, "One More Time", "J", "House", 320),
            SongDTO(songId++, "Harder, Better, Faster, Stronger", "J", "Electronic", 224),
            SongDTO(songId++, "Around the World", "J", "House", 429),
            SongDTO(songId++, "Instant Crush", "J", "Synth-pop", 337),
            
            // Calvin Harris
            SongDTO(songId++, "This Is What You Came For", "K", "EDM", 222),
            SongDTO(songId++, "Summer", "K", "EDM", 222),
            SongDTO(songId++, "Feel So Close", "K", "EDM", 206),
            SongDTO(songId++, "Outside", "K", "EDM", 227),
            SongDTO(songId++, "We Found Love", "K", "EDM", 215)
        )
    }
}
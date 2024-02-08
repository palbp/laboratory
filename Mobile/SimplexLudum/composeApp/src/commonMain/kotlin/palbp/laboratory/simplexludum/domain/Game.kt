package palbp.laboratory.simplexludum.domain

data class Game(
    val name: NonBlankString,
    val developer: NonBlankString,
    val genres: NonEmptyList<Genre>
) {
    constructor(name: String, developer: String, genres: List<Genre>) : this(
        NonBlankString(name),
        NonBlankString(developer),
        NonEmptyList(genres)
    )
}

package palbp.laboratory.simplexludum.domain

// TODO: Represent errors

/**
 * Type used to represent functions responsible for obtaining game lists.
 */
typealias GetGameLists = suspend () -> List<GameListSummary>

/**
 * Type used to represent functions responsible for obtaining the latest games.
 */
typealias GetLatestGames = suspend () -> List<Game>
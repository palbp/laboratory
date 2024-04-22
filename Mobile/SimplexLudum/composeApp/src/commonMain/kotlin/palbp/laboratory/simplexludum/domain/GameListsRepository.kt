package palbp.laboratory.simplexludum.domain

/**
 * Type used to represent functions responsible for obtaining game lists.
 */
typealias GetGameLists = suspend () -> List<GameListSummary>

/**
 * Type used to represent functions responsible for obtaining the latest games.
 */
typealias GetLatestGames = suspend () -> List<Game>

/**
 * Type used to represent functions responsible for obtaining a game list with the
 * given filtering query.
 */
typealias GetGameListWithQuery = suspend (String) -> List<Game>

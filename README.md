# Zoo Animals

This project is demonstrating the use of Clean Code Architecture with MVVM

## Main Libraries Used

- Coroutines
- Retrofit
- MockWebServer
- Kotlin
- Glide
- SwipeRefreshLayout
- io.mockk

## Improvements / Future scope
- With Espresso framework UI / Instrumentation testing can be covered
- We can make use of paging for fetching and rendering bigger list smoothly
- Here we have used repository pattern, if there is complexity in handling locale dataSource, remote dataSource and response caching we can use Interactor pattern along with repository pattern
- UI implementation : Adding comment on Lifespan : can be done in various ways
    * Checking lifespan value and appending comment as per condition in Adapter while setting lifespan
    * Pre-processing lifespan comment and adding extra field in Animal model class and setting value from it
- Adapter : RecyclerView.Adapter vs ListAdapter
    * If we have a static list that doesn’t change once loaded then it is better to use normal Adapter.
    * If Data source is dynamic and something which changes with real-time syncing I would use ListAdapter.
- Logic : 
    * There are some logics / code which can be moved to more appropiate place based on project scaling and future scope
    * Currently written in simple way since this exercise have small scope	    
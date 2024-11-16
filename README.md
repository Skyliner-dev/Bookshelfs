# BookShelf
## This App focuses on how an android repository system works

You can either download the zip or clone this repository and open directly in Android studio
(Recommended to update compileSdk version atleast to 35)

We use ==Retrofit== library to retrieve data from
```https://www.googleapis.com/books/v1/volumes``` 

This is a base url which expects a query parameter, for instance a query to retreive list of jazz books would be `https://www.googleapis.com/books/v1/volumes?q=jazz+history`
You can see more on this api in [BooksApi](https://developers.google.com/books/docs/v1/using)

To deserialize the data received from retrofit we use ==kotlinx.serialization==

Project Structure 
```
Src 
  |_ data 
     |_ repository
     |_ appContainer
  |_ model
     |_ books (data class representing entities in json object deserialised into kotlin objects using @Serializable)
  |_ source
     |_ bookApiSource (retrofit interface which defines how to get data which is used by repository)
  |_ ui (viewModel(ui Logic) and mainScreen)
```  
Key points:
Make sure to replace http to https for displaying images using AsyncImage() composable (we used simple replace() function in kotlin)
Because we dont use all the json objects hosted, we simply ignore the keys which we dont retrieve (this will not try to serialize the ones which we didnt have in data class)
```
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()
```

`val imageLinks: Thumbnails? = null` we make imageLinks optional because only some of the books have json object called imageLinks, null images are handled in the UI part of this application

In The UI we display the images of the books asynchronously by using AsyncImage Composable:
 ```
Column {
        if (thumbnailImageLinks != null ) {
             AsyncImage(
                 model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailImageLinks.thumbnail.replace("http","https"))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().size(250.dp).padding(16.dp)
              )
        } else {
          Text("null")
      }
      Text(book.volumeInfo.title, modifier = Modifier.padding(16.dp))
  }
```
A ViewModel is designed to be a No-Parameter class, thus to use our repository as a parameter we need a viewModel Factory which is a companion object (dont need to instantiate to access this (similar to java static))
In viewModel: (there is only one in this app)
 ```
companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookApplication)
                val bookRepository = application.container.bookRepository
                BookViewModel(bookRepository = bookRepository)
            }
        }
    }
 ``` 
 here BookApplication is needed to add our appContainer to the application by including it when overriding onCreate Method:
 In BookApplication.kt:
```
class BookApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
```
Dont forget to add this in android manifest xml file inside application tag android:name=".BookApplication"
Container in our application as its name suggests acts as the container which gives the dependencies needed throughout the app, Here we initialise retrofit service and NetworkBookRepository (this is exactly what is passed into our viewModel)
by this we are implementing ==dependency injection== (means instead of initialising directly into where its used we loosely couple it and pass as an parameter instead)


[Screenshot](https://github.com/Skyliner-dev/Bookshelfs/blob/master/screenshot.png) of the app.

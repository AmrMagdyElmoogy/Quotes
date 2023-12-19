
![](https://raw.githubusercontent.com/AmrMagdyElmoogy/Quotes/main/app/src/main/res/anim/MergedImages.png)
# Quotes app 
####  "Discover Daily Inspiration with Our App!

### Features

**1- Random Quotes Display :**   
**2- Share any quote to your friends!:**   
**2- Search by Author **  
**3- Search by Content **   
**4-Translation to Arabic (Future Work):**
  
## ** Architecture Overview:**

####    Our project is organized into distinct features, each comprising two essential layers:
  + #### UI Layer:   
       + UI data for displaying random quotes, authors and contents 
	   + UI State for holding states that came from View Model to View,then handle each state indvidually. 
	   
	   
	   
 + ####  ViewModel:
     +  Handles click events of UI components.
     +   Manages state for UI components.
	
	
We have adopted the MVVM architecture with a single activity for our projects. Here are the reasons behind choosing these approaches:

##  **MVVM Architecture**

  ####  Separation of Concerns:   
   - MVVM separates the application logic into three distinct components: Model, View, and ViewModel. 

  #### Reusability :
 - ViewModel instances can be reused across different UI components. This reusability is particularly useful when dealing with multiple fragments. In this project we benefit from this point so much as we have single activity with mutliple fragments.

  #### Flexibility:   
   - MVVM allows for better flexibility when incorporating reactive programming libraries like RxJava or LiveData. This flexibility is especially beneficial when dealing with asynchronous operations and real-time updates. Here we use Coroutine Flow with Room Database to provide us reactive experiment.

  #### Collaboration:   
   - The MVVM architecture is well-suited for collaborative development. The clear separation of responsibilities between team members working on different layers (Model, View, ViewModel) can improve collaboration and reduce conflicts.

  #### Android Architecture Components:
 - MVVM is recommended by Android Architecture Components, a set of libraries provided by Google for building robust, testable, and maintainable Android applications. Components like ViewModel and LiveData complement the MVVM pattern.

  #### Data Binding: 
   - MVVM pairs well with data binding, a feature that connects UI components in the XML layout directly to the ViewModel. This eliminates boilerplate code for updating UI elements and enhances the overall readability of the code.

##     Single Activity Architecture:  

#### Fragment Communication:

  + In a Single Activity architecture with multiple fragments, MVVM facilitates communication between fragments through shared ViewModels. Fragments can observe changes in the ViewModel and update their UI accordingly, promoting modularity and reusability. 
  
####  Easier Navigation:
  + With a Single Activity architecture, navigation can become more straightforward. MVVM allows you to manage navigation logic in the ViewModel, making it independent of the UI components and facilitating a clean navigation flow. 
  
#### Shared ViewModel: 
  + Fragments within the same activity can share a ViewModel. This allows for efficient communication and data sharing between UI components without the need for complex communication mechanisms.
  
####   Resource Efficiency:
 + Having a single activity with multiple fragments is generally more resource-efficient than having multiple activities. This can lead to better performance and a smoother user interface.
  
  
## Libraries and Dependencies: 
 + #### Coil 
   + Coil is a Kotlin-first image loading library for Android.It used for effectient image loading and caching. 
   
 + #### Lottie 
   + Lottie is an animation library by Airbnb that renders After Effects animations in real-time. 
   
 + #### Room 
   + Room is an Android Architecture Component that provides an abstraction layer over SQLite. It supports handling with Coroutine, RxJava and LiveData.
   
 + #### Retrofit 
   + Retrofit is a popular HTTP client for Android that simplifies the process of making network requests.
   
 + #### Moshi 
   + Moshi is a modern JSON library for Kotlin and Java. It simplifies the process of serializing and deserializing JSON data.
   
 + #### OkHttp 
   + OkHttp is an efficient HTTP client for Android and Java applications. It is often used as the underlying network library in conjunction with Retrofit.

 + #### Jetpack Navigation Component 
   +  It provides a framework for managing navigation within an Android app. It used to make transacations amoung fragments.
   
 + #### Fragment KTX 
   + Fragment KTX is a set of Kotlin extensions for the AndroidX Fragment library. It simplifies working with fragments in Kotlin
   
 + #### Coroutines 
   + It is a library for writing asynchronous and concurrent code using the Kotlin programming language
   
 + #### View Model 
   + View Model is a part of the Android Architecture Components, designed to store and manage UI-related data


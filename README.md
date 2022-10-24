Frontend Test Assestment

Option 1 - Mobile native application to show movies using API from https://api.themoviedb.org

User stories: 
1. Create a screen to display the list of official genres for movies. 
2. Create a screen to list of discover movies by genre. 
3. Show the primary information about a movie when user click on of the movie. 
4. Show the users review for a movie. 
5. Show the youtube trailer of the movie. 
6. Implement endless scrolling on list of movies and users review. 
7. Cover positive and negative cases.

Design Pattern : MVVM

Library Used : 
  - Glide : https://github.com/bumptech/glide
  - Retrofit : https://square.github.io/retrofit/
  - Android Youtube Player : https://github.com/PierfrancescoSoffritti/android-youtube-player
  
 Case Found : 
 1. User Review Avatar
    - Positive : 
      Result return image hash for Gravatar or Gravatar link
      ex : 992eef352126a53d7e141bf9e8707576.jpg / https://www.gravatar.com/avatar/992eef352126a53d7e141bf9e8707576.jpg
    
    - Negative : 
      Returning null value for avatar path
    
    - Handling : 
      If avatar path is null, the default avatar from Gravatar will be used. https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?f=y
    
 2. Video Trailer
    - Positive : 
      Result return array of JSON containing data for the video
    
    - Negative : 
      Result return array of JSON containing data for the video with 0 size
    
    - Handling : 
      Instead of youtube trailer, movie backdrop poster will be shown
    
    

# pollen_overview

since i have hay fever and i already feel it again (thanks hazel pollen) i decided it would be fun to implement my own app for checking pollen activity.

i am gonna be using OpenData from MeteoSwiss 

https://opendatadocs.meteoswiss.ch/a-data-groundbased/a7-pollen-stations

## architecture
i am implementing the MVVM + Respository Pattern. (or i am trying to, i havent used them before)

mvvm for the ui & logic 

repository for the api

## features
the app should have these features in the end:
- user should be able to select predefined locations
- user sould be able to select the different kinds of pollen
- based on the selection it should be displayed how strong the pollen activity in the selected area(s) is

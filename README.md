# FoxyCue 
FoxyCue is a lightweight and efficient tool for interacting with the Discogs database and generating .cue sheet for a specific releas

## Attention! This software is designed primarily for analog media releases
For generating .cue files for digital media (CD/DVD), please use more suited programs that have access to online databases (CUETools DB, Musicbrainz) such as [EAC](https://www.exactaudiocopy.de) or [CUETools](http://cue.tools/wiki/CUETools)

# Features
* **Discogs API Integration**: software uses official Discogs API that ensures validity of data 
* **User-Friendly**: one button operation (no brain activity involved)
* **Customizable**: user can edit metadata before .cue generation

# Usage

#### 1. Go to your release page on [Discogs](https://www.discogs.com/)
#### 2. Paste link into _Discogs release link_ field (will be selected by default) and press _Fetch_
#### 3. Edit fields if needed
#### 4. Press _Generate_. You can stop here and copy raw text from right pane
#### 5. Press _Save_ if you want to save .cue. File will be saved in the program root directory
#### 6. You are good to go! Use your DAW or any editing software to fill in track timings

# Technologies/libraries used
* JavaFX - UI
* [Gson](https://github.com/google/gson) - used for json response deserialization into a Release object
* [Discogs API](https://www.discogs.com/developers) - used for retrieving release from Discogs database 

# Looking into the future...
1. [ ] Add logging
2. [ ] Implement custom exceptions
3. [ ] Make permanent user-editable settings
4. [ ] Add individual track info editing from UI
5. [ ] Optimize UI scaling
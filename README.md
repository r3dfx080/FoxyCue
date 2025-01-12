# FoxyCue 
FoxyCue is a lightweight and ~~fluffy~~ efficient tool for interacting with the Discogs database and generating .cue sheet for a specific release. FoxyCue is inteded for analog media _rips_

## :warning: Attention! This software is designed primarily for analog media releases
For generating .cue files for digital media (CD/DVD), please use more suited programs that have access to online databases (CUETools DB, Musicbrainz) such as [EAC](https://www.exactaudiocopy.de) or [CUETools](http://cue.tools/wiki/CUETools)

# Features
* **Discogs API Integration**: software uses official Discogs API that ensures validity of data 
* **User-Friendly**: one button operation (no brain activity involved)
* **Customizable**: user can edit metadata before .cue generation

# Usage

#### 1. Go to your release page on [Discogs](https://www.discogs.com/)
<img src="https://github.com/user-attachments/assets/24bc5d12-d223-4bf4-a591-bdd3061f0a25" width="800">

#### 2. Paste link into _Discogs release link_ field (will be selected by default) and press _Fetch_
<img src="https://github.com/user-attachments/assets/8c52cfb7-af55-4fc3-be9d-b780bb48289a" width="800">

#### 3. Edit fields if needed
#### 4. Press _Generate_. You can stop here and copy raw text from right pane
<img src="https://github.com/user-attachments/assets/5e62c6a3-153f-4ef1-8ce8-d2beac262837" width="800">

#### 5. Press _Save_ if you want to save .cue. File will be saved in the program root directory
<img src="https://github.com/user-attachments/assets/16d16131-a392-4e39-9e7e-249c2661c545" width="500">

#### 6. You are ready to rock! Use your DAW or any editing software to fill in track timings

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


### Development Workflow

*Prerequisites*
1. JDK 8+
1. Ruby (I use 2.4.5)
1. Jekyll (I use 3.8.5)
1. Some kind of simple HTTP server such as Jekyll or Python's SimpleHTTPServer

*Building*

```bash
> sbt
> site/makeMicrosite  # Anytime you edit a source file, repeat this command to see updates in your browser
```

*Testing Locally*

```bash
> jekyll serve --host=0.0.0.0 -s modules/site/target/site
# Should be able to see the site in your browser at: `localhost:4000`
```

Any image manipulating steps in this document are accurate for GIMP 2.8 but Photoshop is probably
very similar if not identical.

### Adding Courses/Holes

1. Create new directory in `modules/site/src/main/resources/microsite/img/golfclash/courses` matching
the new course name.
2. **IMPORTANT**: I use tinypng.com to reduce the size of all images. The site just indexes the
  PNGs so you can probably just do this step in GIMP/Photoshop. The full size maps should be
  in the 140K range.
3. In this new course directory place the following:
  - [ ] Full sized hole maps (named 1.png, 2.png, ..., 9.png)
    * I usually try to keep the height of the image at or below 1200px but that's not critical.
  - [ ] Thumbnail hole maps. These must be 200px x 200px. (named 1-thumb.png, ... 9-thumb.png)
    * To achieve the 200x200 size, I scale the full sized image down to 200px in height and
      resize the canvas in GIMP to 200px wide. This is almost certainly automatable using `imagemagick`.
  - [ ] Scenic image of the course of size 575px x 750px (named scenic.png).
  - [ ] Thumbnail size of the scenic thumb of size 256px x 256x (named scenic-thumb.png).
    * I crop the original 1:1 and scale it down to 256px.
4. Add a new file in `modules/site/src/main/resources/microsite/data/courses/` named `CourseName.yaml`
  1. This file will contain data on each of the 9 holes. Heres' a basic template to follow:
```
courseName: New Course Name
skinned: false
holes:
  - id: <uuid>
    course: New Course Name
    number: 1
    par: 4
    replayName: "4C"
    description: >
      Help Wanted
  - id: <uuid>
    course: New Course Name
    number: 2
    par: 5
    replayName: "5B"
    description: >
      Help Wanted
  ...
  - id: <uuid>
    course: New Course Name
    number: 9
    par: 5
    replayName: "5C"
    description: >
      Help Wanted
```
  1. Each hole ***MUST*** have a unique v4 UUID generated. This is used to reference that hole elsewhere.
  [This site](https://www.uuidgenerator.net/) can be used generate them for you if you need it.
  1. The `replayName` is the name of the hole as it appears in the `Replay` section of the game. This
  identifier is stable. PD however, routinely changes hole numbers. I don't keep up to date because
  I can't play all the courses all the time and it's really not that important. Just busy work.
5. Add a new entry in `modules/site/src/main/resources/microsite/_config.yml`. There's a section
  for courses. You can base the new entry on this template:
```
  ...
  - index_files: true
    data: 'courses.NewCourseName.holes'
    template: '_includes/courses/hole-template.liquid'
    name: 'number'
    dir: 'courses/NewCourseName'
    extra:
      title-prefix: 'New Course Name - Hole '
  ...
```
  1. The `data` and `dir` attributes are the course name *with and spaces and special characters removed*.
6. Rebuild & serve the site locally (SBT & Jekyll).
7. Generate the notesheet (see Notesheet) section.

### Adding Tournaments

1. Create any new holes and their maps (if necessary)
2. Add the tournament banner image (sized 550px x 200px) in the `modules/site/src/main/resources/microsite/img/golfclash/tournaments/titles` directory.
3. Create a new file `NewTournamentName.yaml` in `modules/site/src/main/resources/microsite/data/tournaments`.
  * You can base it on this:
```
tournamentName: New Tournament Name
date: '2019-08-19'
holes:
  - number: 1
    id: <hole 1 uuid>
  - number: 2
    id: <hole 2 uuid>
  - number: 3
    id: <hole 3 uuid>
  - number: 4
    id: <hole 4 uuid>
  - number: 5
    id: <hole 5 uuid>
  - number: 6
    id: <hole 6 uuid>
  - number: 7
    id: <hole 7 uuid>
  - number: 8
    id: <hole 8 uuid>
  - number: 9
    id: <hole 9 uuid>
```
4. Add entry at bottom of `modules/site/src/main/resources/microsite/_config.yml` for the new
tournament. You can base it on entries already in there but it should look similar to:
```
  - index_files: true
    data: 'tournaments.NewTournamentName.holes'
    template: '_includes/tournaments/hole-template.liquid'
    name: 'number'
    dir: 'tournaments/NewTournamentName'
    extra:
      title-prefix: 'New Tournament Name - Hole '
      tournamentName: 'New Tournament Name'
```
5. Create a news item in `modules/site/src/main/resources/microsite/data/news/`.
  1. Named `YEAR-MONTH-DAY-New-Tournament-Name.yaml`
  1. You can base it off of previous tournament announcements but should look similar to:
```
timestamp: 2019-08-13
title: New Tournament Name
content: >
  New Tournament Name tournament holes and notesheets are now available. Find them
  <a href="/tournaments/NewTournamentName/">here.</a>

```
6. Generate the tournament notesheet (see Notesheet) section.

### Creating Notesheets

Once you have the site built and served locally:

1. Depending on if you're make one for a course or a tournament the URL will be one of:
  * Course: `http://localhost:4000/courses/notesheets/NewCourseName/`
  * Tournament: `http://localhost:4000/tournaments/notesheets/NewTournamentName`
2. Create notesheet image:
  - [ ] Navigate to URL in Chrome
  - [ ] Open Developer Tools (`F12`)
  - [ ] `Ctrl` + `Shift` + `P`
  - [ ] `Capture full size screenshot` (as you start typing the command it'll auto complete)
3. Create notesheet PDF:
  - [ ] Crop image to `8.5:11` aspect ratio
  - [ ] `Image` > `Print Size` > `X/Y Resolution` (Set to 150.0)
  - [ ] `Image` > `Scale Image` > (Width 8.5 inches, Height 11.0 inches)
  - [ ] `File` > `Export As` >
    * Course: `modules/site/src/main/resources/microsite/img/golfclash/courses/notesheets/NewCourseName.pdf`
    * Tournament: `modules/site/src/main/resources/microsite/img/golfclash/tournaments/notesheets/NewTournamentName.pdf`

### Adding Balls

1. Add the following images to `modules/site/src/main/resources/microsite/img/golfclash/balls`
(appropriately sized and reduced in size):
  - [ ] NewBallName-32x32.png
  - [ ] NewBallName-64x64.png
  - [ ] NewBallName-256x256.png
2. Create new file in `modules/site/src/main/resources/microsite/data/balls` named `NewBallName.yaml`
2. Should look similar to:
```
name: New Ball Name
windResistance: 3
sideSpin: 3
power: 3
cost: n/a
description: >
  Make something up. Not really that important.
needleSpeed: 0.750
```
3. If you don't know the needle speed, just default to 0.750. Update as necessary.

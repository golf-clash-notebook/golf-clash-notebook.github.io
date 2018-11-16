---
layout: gcn-layout
title:  "Hole Notes"
section: "holenotes"
permalink: /holenotes/
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <h1 class="gcn-page-header">Creating Your Own Hole Notes</h1>

    <h4>Sign In</h4>

    <p>
      To create your own notes, you'll need to first sign in on the Golf Clash Notebook site. You
      can do that by finding the 'Sign In' button in the top right corner of the tool bar on the top
      of any page on the site. After signing in, any notes you create will be saved and stored so
      that whenever you look at a hole, you can see any notes you've taken in the past.
    </p>

    <h4>Basics</h4>

    <p>
      After you've signed in, any time you visit a hole page (e.g., for a <a href="/courses/NamhaeCliffs/1/">course</a> or a <a href="/tournaments/1YearAnniversary/1/">tournament</a>),
      you'll notice an <code>Add Hole Note</code> button. Clicking that button will pop up a new
      window where you can actually create a new note to save.
    </p>

    <p>
      Each hole note has a category. This allows you to keep separate notes for different levels
      of play (e.g. Rookie, Pro, etc). When you create or edit a note, you can select the category
      from the drop down menu. Each category will be shown with it's own distinct color. Easy as pie.
    </p>

    <p>
      Once you've got a category picked out, you can type up the juicy bits that will get you
      that albatross. While notes can contain images, lists and other fancy formatting (see below),
      they can also be plain old text that you can read to dust off any cobwebs from a hole you
      haven't played in a long time. Make them as simple or elaborate as you want.
    </p>

    <p>
      After you've created a note, you'll see it pop up whenever you go back to the same hole page
      (while you're logged in). Each note has buttons to edit or delete that note just in case
      you decide you want to tweak it or just get rid of it.
    </p>

    <h4>Markdown</h4>

    <p>
      If you decide that you do want to put more time into formatting your notes and making them a
      little fancier this is the information you need. Notes support the
      <a href="https://daringfireball.net/projects/markdown/basics" target="_blank" rel="noopener">Markdown</a> format which can
      turn plain old text into HTML with lists, images and more. It's pretty easy to add a small
      amount formatting to create a note that is easier to understand at a glance. It's entirely
      up to you what you want to do!
    </p>

    <p>
      Here's a full example of a note using some Markdown formatting and what it will end up looking
      like:
<pre>
#### Christmas Tournament
![Turkey](/img/golfclash/balls/Turkey-64x64.png)
![Backbone](/img/golfclash/clubs/Backbone-64x64.png)
![NW](/img/golfclash/wind/NW-32x32.png) **10.0**

Max backspin. Play straight at the pin.
</pre>

      Will look like:

      <img src="/img/SampleHoleNote.png" class="img-center img-responsive">
    </p>

    <p>
      Here's a few Markdown basics to get you started. Each example shows how your plain text
      will show up in your hole note.
    </p>

    <dl>
      <dt>Headers</dt>
      <dd>
        <ul>
          <li><code># Header 1</code> ==> <h1 style="display: inline;">Header 1</h1></li>
          <li><code>## Header 2</code> ==> <h2 style="display: inline;">Header 2</h2></li>
          <li><code>### Header 3</code> ==> <h3 style="display: inline;">Header 3</h3></li>
          <li><code>#### Header 4</code> ==> <h4 style="display: inline;">Header 4</h4></li>
          <li><code>##### Header 5</code> ==> <h5 style="display: inline;">Header 5</h5></li>
        </ul>
      </dd>
      <dt>Bold Text</dt>
      <dd><code>This is **bold text**!</code> ==> This is <strong>bold text</strong>!</dd>
      <dt>Emphasized Text</dt>
      <dd><code>This is **emphasized text**!</code> ==> This is <em>emphasized text</em>!</dd>
      <dt>Bold & Emphasized Text</dt>
      <dd><code>This is ***bold & emphasized text***!</code> ==> This is <strong><em>bold & emphasized text</em></strong>!</dd>
      <dt>Links</dt>
      <dd><code>A link to [Tommy's Club Guide](https://youtu.be/pOJYJD_j0cQ)</code> ==> A link to <a href="https://youtu.be/pOJYJD_j0cQ">Tommy's Club Guide</a></dd>
      <dt>Images</dt>
      <dd><code>![Navigator](/img/golfclash/balls/Navigator-32x32.png)</code> ==> <img src="/img/golfclash/balls/Navigator-32x32.png"></dd>
      <dd><code>![Sniper](/img/golfclash/clubs/Sniper-64x64.png)</code> ==> <img src="/img/golfclash/clubs/Sniper-64x64.png"></dd>
      <dd><code>![W](/img/golfclash/wind/W-32x32.png) </code> ==> <img src="/img/golfclash/wind/W-32x32.png"></dd>
      <dd><code>![SSE](/img/golfclash/wind/SSE-32x32.png) </code> ==> <img src="/img/golfclash/wind/SSE-32x32.png"></dd>
    </dl>

    <p>
      As you may have already guessed, you can modify each of the above images to the ball, club or
      wind direction you want, so you can replace 'Navigator' with 'Berserker', 'Sniper' with
      'Cataclysm', etc.
    </p>

    <p>
      There's more that you can do with Markdown so if you're interested, you can read more about
      it <a href="https://daringfireball.net/projects/markdown/">here.</a>
    </p>

  </div>

</div>

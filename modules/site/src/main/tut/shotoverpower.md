---
layout: gcn-layout
title:  "Shot Overpower"
section: "tools"
permalink: /tools/overpower/
---

<h1 class="gcn-page-header">Shot Overpower</h1>

<div class="row">
  <div class="panel panel-default">
    <div class="panel-heading">Club Selection</div>
    <div class="panel-body">
      <div class="col-xs-12">
        <div class="form-group">
          <label for="club{{ clubNum }}-select">Club Category</label>
          <select id="op-club-category-select" class="form-control">
            <option value="drivers">Drivers</option>
            <option value="woods">Woods</option>
          </select>
        </div>
      </div>
      {% for clubNum in (0..1) %}
        <div class="col-xs-6">
          <form>
            <div class="form-group">
              <label for="club{{ clubNum }}-select">Club {{ clubNum }}</label>
              <select id="club{{ clubNum }}-select" class="form-control"></select>
            </div>
            <div class="form-group">
              <select id="club{{ clubNum }}-level-select" class="form-control" disabled></select>
            </div>
            <div class="form-group">
              <select id="club{{ clubNum }}-ball-power-select" class="form-control">
                 <option value="0">Power 0</option>
                 <option value="1">Power 1</option>
                 <option value="2">Power 2</option>
                 <option value="3">Power 3</option>
                 <option value="4">Power 4</option>
                 <option value="5">Power 5</option>
              </select>
            </div>
          </form>
        </div>
      {% endfor %}
    </div>
  </div>
</div>

<div class="row">
  <div class="col-sm-12">
    <div class="op-visual-container">
      <svg id="club-op-plot" class="op-plot">

        <defs>
          <rect id="reticle0-image-clip-rect" x="0" y="0" width="30%" height="95%" rx="5px" ry="5px"/>
          <clipPath id="reticle0-image-clip-path">
            <use xlink:href="#reticle0-image-clip-rect"/>
          </clipPath>
          <rect id="reticle1-image-clip-rect" x="70%" y="0" width="30%" height="95%" rx="5px" ry="5px"/>
          <clipPath id="reticle1-image-clip-path">
            <use xlink:href="#reticle1-image-clip-rect"/>
          </clipPath>
          <linearGradient id="needleGradient" x1="0%" y1="0%" x2="0%" y2="100%">
            <stop offset="0%" style="stop-color:#fbfdfc;stop-opacity:1" />
            <stop offset="50%" style="stop-color:#fcfc85;stop-opacity:1" />
            <stop offset="100%" style="stop-color:#fc0000;stop-opacity:1" />
          </linearGradient>
        </defs>

        <!-- Club 0 -->
        <image id="club0-reticle-image" data-reticle-frame-number="0" xlink:href="/img/golfclash/reticle/small/000.jpg" x="0" y="0" width="30%" height="95%" clip-path="url(#reticle0-image-clip-path)" />
        <rect id="club0-needle-gradient" x="40%" y="3%" width="2%" height="90%" fill="url(#needleGradient)" stroke="#666666" strokeWidth="0.3" />

        <text id="club0-distance" class="reticle-label" x="15%" y="99%" text-anchor="middle" alignment-baseline="baseline" fontFamily="Lato" fontSize="90%">---.- yards</text>

        <text id="club0-extra-yards" class="axis-value" x="39%" text-anchor="end" alignment-baseline="middle" />
        <text id="club0-extra-rings" class="axis-value" x="43%" text-anchor="start" alignment-baseline="middle" />
        <text class="axis-label" x="39%" y="99%" text-anchor="end" alignment-baseline="baseline">Yards</text>
        <text class="axis-label" x="43%" y="99%" text-anchor="start" alignment-baseline="baseline">Rings</text>

        <!-- Club 1 -->
        <image id="club1-reticle-image" data-reticle-frame-number="0" xlink:href="/img/golfclash/reticle/small/000.jpg" x="70%" y="0" width="30%" height="95%" clip-path="url(#reticle1-image-clip-path)"/>
        <rect id="club1-needle-gradient" x="57%" y="3%" width="2%" height="90%" fill="url(#needleGradient)" stroke="#666666" strokeWidth="0.3" />

        <text id="club1-distance" class="reticle-label" x="85%" y="99%" text-anchor="middle" alignment-baseline="baseline" fontFamily="Lato" fontSize="90%">---.- yards</text>

        <text id="club1-extra-rings" class="axis-value" x="56%" text-anchor="end" alignment-baseline="middle" />
        <text id="club1-extra-yards" class="axis-value" x="60%" text-anchor="start" alignment-baseline="middle" />
        <text class="axis-label" x="56%" y="99%" text-anchor="end" alignment-baseline="baseline">Rings</text>
        <text class="axis-label" x="60%" y="99%" text-anchor="start" alignment-baseline="baseline">Yards</text>

      </svg>
      <div>
        <p class="text-center text-semi-muted text-tiny margin-top-8">Drag the images to apply overpower.</p>
      </div>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-lg-10 col-sm-9 col-xs-12 margin-top-32">
    <h4>What is this and why should I care?</h4>
    <p>
      Watching guides on how to play holes in the tournament is a great way to improve your
      finishing position. However, not every tutorial uses the same clubs that you have. This
      can make it tricky to judge what adjustments you need to make in terms of balls and over
      powering your shot to try and mimic the land zone you see in the guide. That's where this
      tool can help you!
    </p>
    <p>
      A perfect example is a guide using an Apocalypse 5+ with a high powered ball and small, to no
      over power applied and you're still sporting level 3/4, Thor's Hammer or maybe an Extra Mile.
      It can be difficult to judge how to match the 240 yards or carry you get with the high level
      Apoc.
    </p>
    <p>
      The first step is to recreate as best you can, the club, level and ball power that you
      watched in the guide. Using the other club in this tool, pick the club you want to use and
      switch up the ball power and amount of overpower, to try and match the overall carry distance
      which you can see below the shot images. Once you've done that, you'll have an educated
      estimate on how to replicate the shot shown in the guide with the clubs/balls you have
      available.
    </p>
    <p>
      For an in depth walk through using this tool, check out
      <a href="https://youtu.be/tBmXJ2iWLXM" target="_blank" rel="noopener">Zachary Jones' YouTube tutorial.</a>
    </p>
    <h4>What will this tool NOT do for me?</h4>
    <p>
      While this tool can get you in the ballpark of where you need to be, <strong>it's still only
      an estimate</strong>. Other factors like elevation and even slight wind speed and angle
      variations can have a significant impact on how your shot will play out. This tool will not
      make your shot for you so ultimately it's up to you to execute!
    </p>
    <p>
      Once you've matched a shot up you'll want to make a note of what ball and amount of over power
      you need to apply. You can remember the position of the ball in relation to the screen or the
      color of the needle as it shifts from white to yellow and red (just remember that there can
      be significant variations within the same needle color). Be as precise as possible but of
      course, your main focus should be getting a shot you're confident with off before you run out
      of time.
    </p>
    <p>
      In the near future, wind will be added to this tool so you can simulate what impact varying
      wind speeds and directions will have. <strong>Until then, and even after, always be aware of
      the subtle wind changes you come across as well as differences in ball wind resistance when
      trying to recreate a shot.</strong>
    </p>
  </div>
</div>
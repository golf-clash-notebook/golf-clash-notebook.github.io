---
layout: gcn-layout
title:  "Shot Overpower"
section: "overpower"
permalink: /overpower/
position: -1
---

<div class="row">
  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <div class="alert alert-warning text-center" role="alert">
      <strong>
        Beware! Here be dragons...
      </strong>
    </div>
  </div>
</div>

<h1 class="gcn-page-header">Shot Overpower</h1>

<div class="row">
  {% for clubNum in (0..1) %}
    <div class="col-xs-6">
      <div class="panel panel-default">
        <div class="panel-heading">Club {{ clubNum | plus: 1 }}</div>
        <div class="panel-body">
          <form class="form-horizontal">
            <div class="form-group">
              <div class="col-xs-12">
                <select id="club{{ clubNum }}-select" class="form-control"></select>
              </div>
            </div>
            <div class="form-group">
              <div class="col-xs-12">
                <select id="club{{ clubNum }}-level-select" class="form-control" disabled></select>
              </div>
            </div>
            <div class="form-group">
              <div class="col-xs-12">
                <select id="club{{ clubNum }}-ball-power-select" class="form-control">
                   <option value="0">Power 0</option>
                   <option value="1">Power 1</option>
                   <option value="2">Power 2</option>
                   <option value="3">Power 3</option>
                   <option value="4">Power 4</option>
                   <option value="5">Power 5</option>
                </select>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  {% endfor %}
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
    </div>
  </div>
</div>

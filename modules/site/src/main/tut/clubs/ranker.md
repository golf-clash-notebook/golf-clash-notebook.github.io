---
layout: gcn-layout
title:  "Club Ranker"
section: "clubs"
permalink: /clubs/ranker/
---

<div class="row">
  <div class="col-md-4 col-sm-6">

    <h1 class="gcn-page-header">Club Ranker</h1>

    <div class="club-ranker-controls">

      <select id="club-category-select" class="form-control">
        <option value="Drivers">Drivers</option>
        <option value="Woods">Woods</option>
        <option value="LongIrons">Long Irons</option>
        <option value="ShortIrons">Short Irons</option>
        <option value="Wedges">Wedges</option>
        <option value="RoughIrons">Rough Irons</option>
        <option value="SandWedges">Sand Wedges</option>
      </select>

      <div class="club-rank-slider-container">
        <div class="text-small">Power</div>
        <input id="power-slider" type="text"
                data-provide="slider"
                data-slider-min="0"
                data-slider-max="100"
                data-slider-step="1"
                data-slider-value="50" />
      </div>

      <div class="club-rank-slider-container">
        <div class="text-small">Accuracy</div>
        <input id="accuracy-slider" type="text"
                data-provide="slider"
                data-slider-min="0"
                data-slider-max="100"
                data-slider-step="1"
                data-slider-value="50" />
      </div>

      <div class="club-rank-slider-container">
        <div class="text-small">Top Spin</div>
        <input id="top-spin-slider" type="text"
                data-provide="slider"
                data-slider-min="0"
                data-slider-max="100"
                data-slider-step="1"
                data-slider-value="50" />
      </div>

      <div class="club-rank-slider-container">
          <div class="text-small">Back Spin</div>
          <input id="back-spin-slider" type="text"
                  data-provide="slider"
                  data-slider-min="0"
                  data-slider-max="100"
                  data-slider-step="1"
                  data-slider-value="50" />
      </div>

      <div class="club-rank-slider-container">
          <div class="text-small">Curl</div>
          <input id="curl-slider" type="text"
                  data-provide="slider"
                  data-slider-min="0"
                  data-slider-max="100"
                  data-slider-step="1"
                  data-slider-value="50" />
      </div>

      <div class="club-rank-slider-container">
          <div class="text-small">Ball Guide</div>
          <input id="ball-guide-slider" type="text"
                  data-provide="slider"
                  data-slider-min="0"
                  data-slider-max="100"
                  data-slider-step="1"
                  data-slider-value="50" />
      </div>
    </div>
  </div>
  <div class="col-md-8 col-sm-6">
    <div id="club-rankings-container" class="club-ranker-rankings table-responsive">
      <table class="table table-centered table-squished table-content-centered table-borderless table-striped">
        <thead>
          <tr>
            <th>Score</th>
            <th>Club</th>
            <th>Level</th>
            <th>Power</th>
            <th>Accuracy</th>
            <th>Top Spin</th>
            <th>Back Spin</th>
            <th>Curl</th>
            <th>Ball Guide</th>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
    </div>
  </div>
</div>


---
layout: gcn-layout
title:  "Wind Chart Creator"
section: "tools"
permalink: /tools/windchartcreator/
---

<div class="row">
  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <h1 class="gcn-page-header margin-bottom-0">Wind Chart Creator</h1>
    <h2 class="gcn-page-subheader">Add your clubs and create your own PDF.</h2>
  </div>
  <br>
</div>

<div id="facebook-browser-warning" class="row hidden">
  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <div class="alert alert-warning text-center" role="alert">
      There's a known issue when trying to create a chart using Facebook's embedded
      browser! For now, make sure you open this page using Chrome, Safari, Firefox, etc.
    </div>
  </div>
</div>

<div class="row">

  <div class="col-sm-6 col-xs-12">
    <form id="add-club-form" class="form-horizontal pad-32">
      <div class="form-group">
        <label for="club-select" class="col-xs-3 text-right">Club</label>
        <div class="col-xs-9">
          <select id="club-select" class="form-control"></select>
        </div>
      </div>
      <div class="form-group">
        <label for="club-level-select" class="col-xs-3 text-right">Level</label>
        <div class="col-xs-9">
          <select id="club-level-select" class="form-control" disabled></select>
        </div>
      </div>
      <div class="col-xs-offset-3 col-xs-9 text-center">
        <button id="club-add-btn" type="button" class="btn btn-default">Add Club</button>
      </div>
    </form>
  </div>
  <div class="col-sm-6 col-xs-12">
    <div id="current-club-list" class="margin-top-16"></div>
    <div class="col-xs-12 text-center margin-top-16">
      <button id="create-wind-chart-btn" type="button" class="btn btn-primary hidden">Create Chart!</button>
    </div>
  </div>
</div>

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
        <label for="club-select" class="col-xs-3 text-right margin-top-6">Club</label>
        <div class="col-xs-9">
          <select id="club-select" class="form-control"></select>
        </div>
      </div>
      <div class="form-group">
        <label for="club-level-select" class="col-xs-3 text-right margin-top-6">Level</label>
        <div class="col-xs-9">
          <select id="club-level-select" class="form-control" disabled></select>
        </div>
      </div>
      <div class="col-xs-offset-3 col-xs-9 text-center">
        <button id="club-add-btn" type="button" class="btn btn-default">Add Club</button>
      </div>
    </form>
  </div>
  <div id="create-chart-controls" class="col-sm-6 col-xs-12 hidden">
    <hr class="hr-text text-large visible-sm visible-xs" data-content="Clubs">
    <form id="add-club-form" class="form-horizontal pad-32">
      <div id="current-club-list" class="margin-top-16 margin-bottom-16"></div>
      <div class="form-group">
        <label for="mode-select" class="col-xs-3 text-right margin-top-6">Title <span class="text-tiny font-500">(optional)</span></label>
        <div class="col-xs-9">
          <input id="title-input" type="text" maxLength="48" class="form-control">
        </div>
      </div>
      <div class="form-group">
        <label for="mode-select" class="col-xs-3 text-right margin-top-6">
          Mode
          <button type="button" class="btn btn-default btn-inline wind-chart-mode-info-btn" data-toggle="modal" data-target="#mode-info-modal">
            <i class="far fa-question-circle"></i>
          </button>
        </label>
        <div class="col-xs-9">
          <select id="mode-select" class="form-control"></select>
        </div>
      </div>
      <div class="col-xs-12 text-center margin-top-16">
        <button id="create-wind-chart-btn" type="button" class="btn btn-primary">Create Chart!</button>
      </div>
    </form>
  </div>

</div>

<!-- Info Modal -->
<div class="modal fade" id="mode-info-modal" tabindex="-1" role="dialog" aria-labelledby="Chart Mode Information">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <div class="row margin-8">
          <div class="col-xs-12">
            <button type="button" class="close pull-right" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4>Wind Chart Modes</h4>
            <p>
              The important thing to say up front is that every mode will get you in the
              neighborhood of where you need to be. Each mode is just a way of fine tuning your
              adjustments based on which ball you're using. Typically they won't make a huge
              difference unless you're up against some extreme winds. You could also see
              improvements if you use a 'Power 5' chart when using a Berserker/Snow Globe vs. using
              a low power chart due to the additional flight time of the ball.
            </p>
            <dl>
              <dt>Power 'X'</dt>
              <dd>
                All of the Power modes take into account the actual flight distance of the shot
                when using a ball with the given Power ability. To be precise, a Sniper is only a
                1 ring adjustment per 1 MPH when you're carrying it 180 yards. When using a
                basic ball the wind will affect the flight of the ball much less than if you were
                to use a Berserker since a Power 5 ball will be in the air for much longer than the
                basic ball. These charts are more accurate to the type of ball you're using so if
                you generally like to use Quasars or Navigators, try out 'Power 1' or if you like to
                use Titans or King Makers go with 'Power 3'. Decide for yourself if this level of
                nerdom fits you. While carrying around 6 charts for each ball power level is likely
                overkill, keep in mind the micro adjustments that may come when switching between
                balls.
              </dd>
              <dt>Simple</dt>
              <dd>
                This is typically what you'll see when you look at the various wind charts floating
                around the web. And this was the method used on Golf Clash Notebook before the other
                options were introduced so if you're happy with the chart you had, then go ahead and
                stick with this. It basically assumes that every club in a given category
                has the same max distance (e.g. Drivers: 240 yards, Woods: 180 yards, etc.). This
                obviously isn't an accurate assumption but this mode will generally be 'accurate
                enough' for most situations.
              </dd>
            </dl>
          </div>
        </div>
        <div class="row margin-8">
          <div class="col-xs-12 text-center">
            <button type="button" class="btn btn-default btn-slim" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
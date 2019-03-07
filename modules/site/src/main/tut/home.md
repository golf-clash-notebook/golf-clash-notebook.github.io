---
layout: gcn-layout
title:  "Home"
section: "home"
permalink: /
position: 0
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    {% if site.data.motd.message != undefined %}
      <div class="alert alert-warning text-center" role="alert">
        {{ site.data.motd.message }}
      </div>
    {% endif %}

    <br class="visible-xs">

    <div class="news-container">
      {% include news/feed-widget.liquid %}
    </div>

    <p class="text-content">
      An open source compilation of Golf Clash content. Here you'll find past guides, descriptions,
      tips and more for the game of Golf Clash that may help you along your way.
    </p>

    <p class="text-content">
      This site is very much a work in progress and wouldn't be possible without the great work
      already done by other golf clash players. As you look through the site you'll probably see a
      lot of filler text. That's to let you know that it needs to be filled in and you can do it!
    </p>

    <p class="text-content">
      Be sure to check out the <a href="/resources/">Resources</a> page which provides links to
      other pages that have a lot of quality content. And lastly, all community contributions are
      appreciated. You can find out how you can do just that on the <a href="/contributing/">Contributing Page.</a>
    </p>

  </div>

</div>

<div class="row">
  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    {% include schedule/stream-schedule.liquid %}
  </div>
</div>

<div class="row">

  <div class="col-xs-12">
    <hr class="hr-text text-large" data-content="Features">
  </div>

  <div class="col-lg-3 col-lg-offset-0 col-md-4 col-md-offset-2 col-sm-5 col-sm-offset-1 col-xs-10 col-xs-offset-1 col-flex" data-mh="feature-card">
    <div class="site-feature-card text-center">
      <h4>Animated Hole Guides</h4>
      <p class="feature-description text-small text-semi-muted">
        When you run into a tricky hole, find it in the notebook and you'll see which ways will lead you to success.
      </p>
      <a href="/courses/SouthernPines/4/" class="btn btn-primary feature-link" role="button">Check it Out</a>
    </div>
  </div>

  <div class="col-lg-3 col-lg-offset-0 col-md-4 col-sm-5 col-sm-offset-0 col-xs-10 col-xs-offset-1 col-flex" data-mh="feature-card">
    <div class="site-feature-card text-center">
      <h4>Custom Wind Charts</h4>
      <p class="feature-description text-small text-semi-muted">
        Create a wind chart specifically for your bag with adjustments for Min, Mid & Max club distances.
      </p>
      <a href="/tools/windchartcreator/" class="btn btn-primary feature-link" role="button">Check it Out</a>
    </div>
  </div>

  <div class="col-lg-3 col-lg-offset-0 col-md-4 col-sm-5 col-sm-offset-0 col-xs-10 col-xs-offset-1 col-flex" data-mh="feature-card">
    <div class="site-feature-card text-center">
      <h4>Note Sheets</h4>
      <p class="feature-description text-small text-semi-muted">
        A quick printout will get you set up to take all the notes you'll need to conquer any holes that get thrown your way.
      </p>
      <a href="/img/golfclash/tournaments/notesheets/FestiveCup.pdf" class="btn btn-primary feature-link" role="button">Check it Out</a>
    </div>
  </div>

  <div class="col-lg-3 col-lg-offset-0 col-md-4 col-md-offset-2 col-sm-5 col-sm-offset-1 col-xs-10 col-xs-offset-1 col-flex" data-mh="feature-card">
    <div class="site-feature-card text-center">
      <h4>Club Ranking</h4>
      <p class="feature-description text-small text-semi-muted">
        Completely customizable ranking tool will show you which clubs you should be using on the course.
      </p>
      <a href="/tools/clubranker/" class="btn btn-primary feature-link" role="button">Check it Out</a>
    </div>
  </div>

  <div class="col-xs-12">
    <hr class="hr-text text-large margin-4">
  </div>

</div>

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <p class="text-center pad-24">
      A few shoutouts to people whose contributions to the community have made this site what it is.
      A big thank you!
    </p>

    <div class="center-block">
      <div class="media">
        <div class="media-left">
          <a href="https://www.facebook.com/teamgolfclashtommy/" target="_blank" rel="noopener">
            <i class="fab fa-facebook fa-3x fa-fw text-facebook" aria-hidden="true"></i>
          </a>
        </div>
        <div class="media-body">
          <h4 class="media-heading">Team Golf Clash Tommy</h4>
          They do a lot of amazing work creating guides to help a lot of people.
        </div>
      </div>

      <div class="media">
        <div class="media-left">
          <a href="https://www.youtube.com/channel/UCovksimHM7c7dKVb6oRVUhg" target="_blank" rel="noopener">
            <i class="fab fa-youtube fa-3x fa-fw text-youtube" aria-hidden="true"></i>
          </a>
        </div>
        <div class="media-body">
          <h4 class="media-heading">Joni Koskinen</h4>
          His <a href="https://docs.google.com/spreadsheets/d/15E32sH-6VJ563I6gnhbhWXHN4dqGKSM11L8OMSbGyAg/edit#gid=71066884" target="_blank" rel="noopener">club stat spreadsheet</a> helped fill in some missing holes.
        </div>
      </div>

      <div class="media">
        <div class="media-left">
          <i class="fab fa-reddit-square fa-3x fa-fw text-reddit" aria-hidden="true"></i>
        </div>
        <div class="media-body">
          <h4 class="media-heading">/u/SeeZee21</h4>
          For coming up with an FAQ structure that makes sense and getting a lot of great answers.
        </div>
      </div>
    </div>

    <p class="text-center text-semi-muted pad-24">
      A full list of site contributors can be found on the <a href="/contributing#ContributorList">Contributors</a> page.
    </p>

  </div>

</div>

{% include news/app-announcement-modal.liquid %}

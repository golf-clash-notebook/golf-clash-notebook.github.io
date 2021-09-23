---
layout: gcn-layout
title:  "Power Slice"
section: "powerslice"
permalink: /powerslice/
position: 72
---

<h1 class="gcn-page-header">Power Slice Ring Adjustments</h1>

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <div class="alert alert-warning text-center" role="alert">
      <strong>
        Before you read through this page take a word of caution that this type of shot is as much of
        an art as it is a science and <em><strong>it will take practice to get good at it.</strong></em>
        That point can't be stressed enough. There are many different variables that contribute to a
        successfully executed power hook/slice (e.g. wind direction, wind intensity, elevation
        changes, curl, etc.) So take the information available on this page as a starting point if
        you need one, and then continue to work on perfecting your technique.
      </strong>
    </div>
  </div>

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <p>
      Below is a chart of ring adjustments you can to use when playing a power hook or slice with
      your driver. For those that are curious, this chart was created using 'known' adjustment
      values used by players far more skilled than myself and using
      <a href="https://en.wikipedia.org/wiki/Linear_regression" target="_blank" rel="noopener">linear regression</a>
      to fill in values for every club and every level. In no way am I saying this is the best
      approach, but it's one way to get a starting point for your adjustments.
    </p>

    {% include powerslice/adjustment-chart.liquid title='Golf Clash Notebook' clubs=site.data.clubs.drivers powerCoefficient=0.0 accuracyCoefficient=0.093 curlCoefficient=0.08125 alpha=5.82284 %}

    {% comment %}

    {% include powerslice/adjustment-chart.liquid title='Nathan Taylor' powerCoefficient=0.0 accuracyCoefficient=0.07518 curlCoefficient=0.03782 alpha=7.65162 %}

    {% include powerslice/adjustment-chart.liquid title='Nathan Taylor - Updated' powerCoefficient=0.0 accuracyCoefficient=0.07586 curlCoefficient=0.0 alpha=9.54034 %}

    {% include powerslice/adjustment-chart.liquid title='Crowd Reaction' powerCoefficient=0.0 accuracyCoefficient=0.05679 curlCoefficient=0.01055 alpha=11.1086 %}

    {% endcomment %}

  </div>

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <hr class="hr-text text-large" data-content="Resources">

    <h4>Zachary Jones</h4>
    <p>
      I would <em><strong>strongly</strong></em> encourage you to watch Zachary Jones's YouTube
      tutorials on playing power hooks/slices
      (<a href="https://youtu.be/HF1pmPqOuWs" target="_blank" rel="noopener">Part 1</a> &
      <a href="https://youtu.be/N6pFGayD8XQ" target="_blank" rel="noopener">Part 2</a>). They
      address many different aspects/subtleties of the shot and can very quickly take you from a
      complete beginner to someone capable of pulling off some pretty impressive shots.
    </p>

    <h4>Nathan Taylor</h4>
    <p>
      Nathan Taylor has also done some incredible work on finding accurate ring adjustments for
      power hooks/slices. He has an <a href="https://www.facebook.com/groups/golfclashelite/permalink/1884913214860458/" target="_blank" rel="noopener">in depth post about his adjustment figures</a>. Here are a few links to his charts:
      <ul>
        <li><a href="/img/golfclash/hookslice/hook-slice-drivers.jpg" target="_blank" rel="noopener">Drivers</a></li>
        <li><a href="/img/golfclash/hookslice/hook-slice-woods.jpg" target="_blank" rel="noopener">Woods</a></li>
        <li><a href="/img/golfclash/hookslice/hook-slice-long-irons.jpg" target="_blank" rel="noopener">Long Irons</a></li>
        <li><a href="/img/golfclash/hookslice/hook-slice-short-irons.jpg" target="_blank" rel="noopener">Short Irons</a></li>
        <li><a href="/img/golfclash/hookslice/hook-slice-rough-irons.jpg" target="_blank" rel="noopener">Rough Irons</a></li>
        <li><a href="/img/golfclash/hookslice/hook-slice-sand-wedges.jpg" target="_blank" rel="noopener">Sand Wedges</a></li>
      </ul>
    </p>
  </div>

</div>

{% comment %}

http://www.socscistatistics.com/tests/multipleregression/Default.aspx

General Notes
====================================
* Driver fitting does not apply to long irons

# My Observations
---------------------------------
Rocket       9 |  83 |  54 | 16 |
Extra Mile   6 |  29 |  25 | 12 |
Big Topper   4 |  15 |  65 | 12 |
Quarterback  8 | 100 |  82 | 23 |
Rock         4 |  86 |  53 | 19 |
Thors Hammer 4 |  64 |  41 | 14 |

0.093[acc] + 0.08125[curl] + 5.82284

# Zachary Jones (woods)
---------------------------------
Horizon     1 |   0 |   40 | 14 |
Viper       5 |  51 |   36 | 15 |
Big Dawg    3 |  25 |   66 | 14 |
Hammerhead  3 |  83 |   71 | 19 |
Guardian    8 |  90 |   67 | 25 |
Sniper     10 | 100 |   95 | 30 |
Cataclysm   6 |  60 |  100 | 15 |
Cataclysm   7 |  80 |  100 | 20 |


{% endcomment %}
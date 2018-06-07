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
        <li><a href="https://scontent.fzty1-1.fna.fbcdn.net/v/t1.0-9/30688774_10216600959524881_2411520503438114816_n.jpg?_nc_cat=0&oh=b935c454c3e845bf75c743455f0f34c8&oe=5B921F66" target="_blank" rel="noopener">Drivers</a></li>
        <li><a href="https://scontent.fzty1-1.fna.fbcdn.net/v/t1.0-9/30652974_10216600959204873_5473254679888527360_n.jpg?_nc_cat=0&oh=6d419b3679073a8b35a67a4e2682c87a&oe=5B96B5B2" target="_blank" rel="noopener">Woods</a></li>
        <li><a href="https://scontent.fzty1-1.fna.fbcdn.net/v/t1.0-9/30624559_10216600959484880_4961208975212150784_n.jpg?_nc_cat=0&oh=02b9d98d2cb5139681cdff6d47208f8c&oe=5B762993" target="_blank" rel="noopener">Long Irons</a></li>
        <li><a href="https://scontent.fzty1-1.fna.fbcdn.net/v/t1.0-9/30656871_10216600959564882_4386773772435193856_n.jpg?_nc_cat=0&oh=692abafbedc6e4f52e5e7ef2252320e3&oe=5B81F02C" target="_blank" rel="noopener">Short Irons</a></li>
        <li><a href="https://scontent.fzty1-1.fna.fbcdn.net/v/t1.0-9/30623697_10216600959444879_1937950760771780608_n.jpg?_nc_cat=0&oh=aeaf78bf1a282caeaf585b0a6ee92f18&oe=5B8F0B79" target="_blank" rel="noopener">Rough Irons</a></li>
        <li><a href="https://scontent.fzty1-1.fna.fbcdn.net/v/t1.0-9/30629570_10216600959244874_1275727006285168640_n.jpg?_nc_cat=0&oh=eb1728f6a9b47b2aeb7a121d88b5f456&oe=5BC06128" target="_blank" rel="noopener">Sand Wedges</a></li>
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
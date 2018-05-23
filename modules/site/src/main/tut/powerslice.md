---
layout: gcn-layout
title:  "Power Slice / Hook"
section: "powerslice"
permalink: /powerslice/
position: -1
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
      approach, but it's one way to get a starting point for you adjustments.
    </p>

    {% include powerslice/adjustment-chart.liquid title='Golf Clash Notebook' powerCoefficient=0.0 accuracyCoefficient=0.093 curlCoefficient=0.08125 alpha=5.82284 %}

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

# Nathan Taylor
---------------------------------
Rocket       1 | 190 |  50 |  12 | 10 |
Rocket       2 | 190 |  70 |  12 | 12 |
Rocket       3 | 192 |  70 |  26 | 12 |
Rocket       4 | 198 |  76 |  26 | 14 |
Rocket       5 | 198 |  76 |  30 | 14 |
Rocket       6 | 202 |  83 |  30 | 15 |
Rocket       7 | 204 |  83 |  44 | 15 |
Rocket       8 | 204 |  83 |  54 | 15 |
Rocket       9 | 206 |  83 |  54 | 15 |
Rocket      10 | 211 |  90 |  54 | 16 |
Extra Mile   1 | 220 |   0 |  15 | 12 |
Extra Mile   2 | 220 |   7 |  15 | 12 |
Extra Mile   3 | 224 |   7 |  15 | 12 |
Extra Mile   4 | 226 |   7 |  25 | 12 |
Extra Mile   5 | 226 |  29 |  25 | 12 |
Extra Mile   6 | 234 |  29 |  30 | 12 |
Extra Mile   7 | 234 |  45 |  47 | 14 |
Extra Mile   8 | 236 |  45 |  47 | 14 |
Big Topper   1 | 205 |   0 |  33 |  8 |
Big Topper   2 | 209 |   0 |  38 |  8 |
Big Topper   3 | 209 |  15 |  54 | 10 |
Big Topper   4 | 217 |  15 |  65 | 10 |
Quarterback  1 | 197 |  73 |  35 | 14 |
Quarterback  2 | 197 |  80 |  35 | 15 |
Quarterback  3 | 203 |  80 |  40 | 15 |
Quarterback  4 | 203 |  80 |  53 | 15 |
Quarterback  5 | 207 | 100 |  58 | 18 |
Quarterback  6 | 207 | 100 |  58 | 18 |
Quarterback  7 | 214 | 100 |  67 | 18 |
Quarterback  8 | 214 | 100 |  82 | 20 |
Quarterback  9 | 218 | 100 |  86 | 20 |
Quarterback 10 | 218 | 100 |  86 | 20 |
Rock         1 | 210 |  65 |  38 | 15 |
Rock         2 | 210 |  65 |  43 | 15 |
Rock         3 | 210 |  86 |  53 | 16 |
Rock         4 | 217 |  86 |  53 | 16 |
Rock         5 | 217 |  94 |  68 | 16 |
Rock         6 | 221 |  94 |  68 | 16 |
Rock         7 | 221 | 100 |  79 | 18 |
Rock         8 | 226 | 100 |  96 | 18 |
Thors Hammer 2 | 220 |  64 |  30 | 14 |
Thors Hammer 3 | 222 |  64 |  30 | 14 |
Thors Hammer 4 | 229 |  64 |  41 | 14 |
Thors Hammer 5 | 232 |  64 |  41 | 14 |
Thors Hammer 6 | 232 |  64 |  41 | 14 |
Apocalypse   1 | 229 |  35 |  76 | 12 |
Apocalypse   2 | 231 |  35 |  76 | 12 |
Apocalypse   3 | 231 |  43 |  92 | 14 |
Apocalypse   4 | 234 |  66 |  92 | 15 |

<!-- 0.07518[acc] + 0.03782[curl] + 7.65162 -->

# GCE Comments
---------------------------------
Extra Mile   4 |   7 |  25 | 12 |
Extra Mile   6 |  29 |  30 | 13 |
Extra Mile   7 |  45 |  47 | 13 |
Extra Mile   7 |  45 |  47 | 15 |
Extra Mile   8 |  45 |  47 | 15 |
Thors Hammer 3 |  64 |  30 | 15 |
Thors Hammer 4 |  64 |  41 | 15 |
Apocalypse   3 |  43 |  92 | 14 |
Apocalypse   3 |  43 |  92 | 15 |
Apocalypse   4 |  66 |  92 | 13 |
Apocalypse   4 |  66 |  92 | 16 |
Apocalypse   5 |  75 |  92 | 15 |
Apocalypse   6 |  75 |  98 | 20 |

0.05679[acc] + 0.01055[curl] + 11.1086

{% endcomment %}
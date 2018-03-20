---
layout: gcn-layout
title:  "Power Slice / Hook"
section: "powerslice"
permalink: /powerslice/
position: -1
---

<h1 class="gcn-page-header">Power Slice Ring Adjustments</h1>

<div class="row">

  <div class="alert alert-warning text-center" role="alert">
    <strong>
      If you're on this page then you should already know that this information is <em>NOT</em>
      proven. These numbers are only used as a rough estimate and each chart uses a different set
      of input to generate the values you see. Once you add different balls, wind, elevation, etc.
      these charts will become much less accurate. USE AT YOUR OWN RISK!
    </strong>
  </div>

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    {% include powerslice/adjustment-chart.liquid title='Golf Clash Notebook' powerCoefficient=0.0 accuracyCoefficient=0.093 curlCoefficient=0.08125 alpha=5.82284 %}

    {% include powerslice/adjustment-chart.liquid title='Nathan Taylor' powerCoefficient=0.0 accuracyCoefficient=0.07518 curlCoefficient=0.03782 alpha=7.65162 %}

    {% include powerslice/adjustment-chart.liquid title='Nathan Taylor - Updated' powerCoefficient=0.0 accuracyCoefficient=0.07586 curlCoefficient=0.0 alpha=9.54034 %}

    {% include powerslice/adjustment-chart.liquid title='Crowd Reaction' powerCoefficient=0.0 accuracyCoefficient=0.05679 curlCoefficient=0.01055 alpha=11.1086 %}

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
---
layout: gcn-layout
title:  "Chests"
section: "chests"
permalink: /chests/
position: 55
---

<p class="lead text-center">Chest Contents</p>

<div class="row">

  <div class="col-lg-10 col-lg-offset-1 col-md-12 table-responsive">

  <table class="table table-centered table-content-centered table-squished table-nowrap">
    <thead>
      <tr>
        <th class="text-center">Tour</th>
        <th class="text-center"><img src="/img/golfclash/chests/Wooden-thumb.png" title="Wooden Chest"></th>
        <th class="text-center"><img src="/img/golfclash/chests/Silver-thumb.png" title="Silver Chest"></th>
        <th class="text-center"><img src="/img/golfclash/chests/Gold-thumb.png" title="Gold Chest"></th>
        <th class="text-center"><img src="/img/golfclash/chests/Platinum-thumb.png" title="Platinum Chest"></th>
        <th class="text-center"><img src="/img/golfclash/chests/King-thumb.png" title="King Chest"></th>
      </tr>
    </thead>
    <tbody>
      {% for tour in (1..13) %}

        {% assign tourIx = tour | minus: 1 %}
        {% assign wooden = site.data.chests[tourIx].wooden %}
        {% assign silver = site.data.chests[tourIx].silver %}
        {% assign gold = site.data.chests[tourIx].gold %}
        {% assign platinum = site.data.chests[tourIx].platinum %}
        {% assign king = site.data.chests[tourIx].king %}

        <tr>
          <th>{{ tour }}</th>
          <td>{% include_relative chests/chest-stats-template.liquid chest=wooden %}</td>
          <td>{% include_relative chests/chest-stats-template.liquid chest=silver %}</td>
          <td>{% include_relative chests/chest-stats-template.liquid chest=gold %}</td>
          <td>{% include_relative chests/chest-stats-template.liquid chest=platinum %}</td>
          <td>{% include_relative chests/chest-stats-template.liquid chest=king %}</td>
        </tr>
      {% endfor %}
    </tbody>
    <tfoot>
      <tr>
        <th class="text-right" colspan="6">
          <img class="pad-4" src="/img/golfclash/chests/Epic-thumb.png"> Guaranteed Epics
          <img class="pad-4" src="/img/golfclash/chests/Rare-thumb.png"> Guaranteed Rares
          <img class="pad-4" src="/img/golfclash/chests/Common-thumb.png"> Guaranteed Total
        </th>
      </tr>
    </tfoot>
  </table>

  </div>

</div>

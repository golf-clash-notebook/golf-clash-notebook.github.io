---
layout: gcn-layout
title:  "Courses"
section: "courses"
permalink: /courses/
position: 20
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-sm-12">

    {% capture courseKeys %}
      {% for tag in site.data.courses %}
        {{ tag[0] }}
      {% endfor %}
    {% endcapture %}

    {% assign sortedKeys = courseKeys | split: ' ' | sort %}
    {% assign mod2 = sortedKeys | size | modulo: 2 %}
    {% assign mod3 = sortedKeys | size | modulo: 3 %}

    {% for courseKey in sortedKeys %}

      {% assign course = site.data.courses[courseKey] %}
      {% assign coursePath = course.name | remove: " " | remove: "'" %}

      {% capture columnClasses %}
        {% if mod3 == 1 and forloop.last %}
          col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12
        {% elsif mod3 == 2 and forloop.rindex == 2 %}
          col-md-4 col-md-offset-2 col-sm-6 col-xs-12
        {% elsif mod3 == 2 and forloop.last %}
          col-md-4 col-md-offset-0 col-sm-6 col-sm-offset-3 col-xs-12
        {% elsif mod2 == 1 and forloop.last %}
          col-md-4 col-md-offset-0 col-sm-6 col-sm-offset-3 col-xs-12
        {% else %}
          col-md-4 col-sm-6 col-xs-12
        {% endif %}
      {% endcapture %}

      <div class="{{ columnClasses | strip }} text-center">
        <div class="panel panel-default">
          <div class="panel-heading">
            <a href="/courses/{{ coursePath }}/">
              {{ course.name }}
            </a>
          </div>
          <div class="panel-body">
            <a href="/courses/{{ coursePath }}/">
              <img class="img-responsive img-center" src="/img/golfclash/courses/{{ coursePath }}/thumb.png">
            </a>
          </div>
        </div>
      </div>

    {% endfor %}

  </div>

</div>

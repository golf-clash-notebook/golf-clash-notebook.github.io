---
layout: gcn-layout
title:  "FAQ"
section: "faq"
permalink: /faq/
position: 70
---

{% capture categoryAndPosition %}
  {% for tag in site.data.faq %}
    {{ tag[1].position }}:{{ tag[0] }}
  {% endfor %}
{% endcapture %}

{% assign sortedComposite = categoryAndPosition | split: ' ' | sort %}

{% capture sortedCategories %}
  {% for x in sortedComposite %}
    {{ x | split: ':' | last }}
  {% endfor %}
{% endcapture %}

{% assign sortedCategoryArray = sortedCategories | split: ' ' %}

<div class="row">

  <div id="faq-container" class="col-lg-10 col-sm-9 col-xs-12">

    <h1 class="gcn-page-header">Answers to the most common Golf Clash questions.</h1>

    <p class="text-center">
      <a href="https://github.com/golf-clash-notebook/golf-clash-notebook.github.io/issues/new" class="btn btn-info" role="button">Add to the FAQ</a>
    </p>

    {% for faqCategoryKey in sortedCategoryArray %}
      {% assign faqCategory = site.data.faq[faqCategoryKey] %}

      {% assign categoryId = faqCategory.category | remove: " " %}

      <h3 id="{{ categoryId }}" class="faq-category-header text-center text-semi-muted">{{ faqCategory.category }}</h3>

      {% for question in faqCategory.questions %}
        <h4 class="faq-question">{{ question.question }}</h4>

        {% if question.answer contains 'Help Wanted' %}
          <p class="faq-answer text-prototype">
            Help Wanted: {% lipsum 1 10 15 %}
          </p>
        {% else %}
          <div class="faq-answer">
            {{ question.answer }}
          </div>
        {% endif %}
      {% endfor %}
    {% endfor %}

  </div>

  <div class="col-lg-2 col-sm-3 hidden-xs">

    <div id="faq-scrollspy-nav" data-spy="affix" data-offset-top="60" >
      <ul class="nav">
        {% for faqCategoryKey in sortedCategoryArray %}
          {% assign faqCategory = site.data.faq[faqCategoryKey] %}
          {% assign categoryId = faqCategory.category | remove: " " %}
          <li>
            <a class="text-semi-muted" href="#{{ categoryId }}">{{ faqCategory.category }}</a>
          </li>
        {% endfor %}
      </ul>
    </div>

  </div>

</div>

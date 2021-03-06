---
layout: gcn-layout
title:  "Tournaments"
section: "tournaments"
permalink: /tournaments/
position: 10
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-sm-12">

    {% capture dateAndKeys %}
      {% for tag in site.data.tournaments %}
        {% if tag[1].hidden == undefined %}
          {{ tag[1].date }}:{{ tag[0] }}
        {% endif %}
      {% endfor %}
    {% endcapture %}

    {% assign sortedComposite = dateAndKeys | split: ' ' | sort %}

    {% capture sortedKeys %}
      {% for x in sortedComposite %}
        {{ x | split: ':' | last }}
      {% endfor %}
    {% endcapture %}

    {% assign keyArray = sortedKeys | split: ' ' | reverse %}
    {% assign mod2 = keyArray | size | modulo: 2 %}

    {% for key in keyArray %}

      {% capture columnClasses %}
        {% if mod2 == 1 and forloop.last %}
          col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-12
        {% else %}
          col-md-6 col-xs-12
        {% endif %}
      {% endcapture %}

      <div class="{{ columnClasses | strip }} text-center pad-16">
        <a href="/tournaments/{{ key }}/">
          <img class="img-responsive img-center" src="/img/golfclash/tournaments/titles/{{ key }}-550x200.png">
        </a>
      </div>

    {% endfor %}

  </div>

</div>

<hr>

<div class="row">

  <div class="col-md-4 col-md-offset-4 col-sm-12">
    <img src="/img/golfclash/tournaments/rules/Rules.png" class="img-center img-responsive" style="margin: 20px 0;">
  </div>

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <h4>Understanding the Basics</h4>
    <p>
      Tournament play isn't much more complicated than typical 1 vs. 1 matches. In fact a tournament
      just consists of playing a lot of 1 vs. 1 games on a predetermined set of holes over the
      course of 3 rounds. The ultimate goal is to get the lowest score in each round so that you
      can gradually advance to the final round where you can collect prizes. The better you finish,
      the better your prizes will be.
    </p>
    <p>
      It's important to understand that winning matches, while good to do, isn't the most important thing
      to focus on. The primary goal is to score well on the regulation holes (i.e. not shootouts).
      Shootouts have no effect on you tournament score. So if you find yourself losing a bunch of
      holes on shootouts, don't get too concerned as long as you're scoring well on the holes that
      matter and your bankroll isn't getting too low.
    </p>
    <p>
      Each tournament consists of 3 rounds that are played throughout 1 week. During each round
      you'll be placed in a bracket with a group of other players.
    </p>

    <h4>Understanding Tournament Levels</h4>
    <p>
      There are 4 levels of tournament that you can enter: Rookie, Pro, Expert, Masters. Which
      level(s) you're able to enter depends on which division you're currently in. If you're in
      Rookie 1,2,3, then the answer is simple because you'll only be able to enter the Rookie
      tournament. On the other hand, if you're in the Pro, Expert or Master divisions, you can
      enter any level at or below where you're currently at (e.g. a player in Expert 3 can enter
      the Rookie, Pro or Expert tournament). Each tour has better prizes, both overall and for
      winning individual holes. For winning a single hole at each level you'll receive (assuming
      you have an open chest slot) a Tour 3, 6, 9, 12 chest respectively. And at each level, both
      the wind and competition increase so it's worth thinking about which level to pick (see
      <a href="#ChoosingTournamentLevel">Choosing Tournament Level</a>).
    </p>

    <h4>Understanding the Wind</h4>
    <p>
      An important point to mention is how the wind works in the tournament. Each tournament
      level has progressively higher winds, much like the tours in regular 1 vs. 1 play. Be sure to
      pay attention to how much wind and the wind direction on each hole. These will remain roughly
      the same throughout all rounds of the tournament. Knowing that you'll always have head/tail/side
      wind on a given hole can let you plan which clubs and balls you'll need in advance. So take
      notes if you need to during the early rounds so you'll be prepared for the weekend.
    </p>

  </div>

  <div class="col-lg-12"><hr></div>

  <div class="col-md-4 col-sm-12">
    <img src="/img/golfclash/tournaments/rules/Qualifying.png" class="img-responsive pad-16">
    <p>
      The first round will put you up against a set of 9 holes. There are actually 3 qualifying
      rounds but you'll only need to be successful in 1 to advance. Each qualification attempt
      will place you in a bracket of 20 people and those players with the top 10 scores will
      advance. These rounds are usually played on Monday, Tuesday and Wednesday so if you don't
      make it on Monday, you'll still have a chance to qualify on Tuesday and Wednesday. Keep in
      mind that you'll have to ante up the entry fee again for each attempt. If you don't make it
      on Wednesday, you'll be sitting out the rest of the tournament.
    </p>
    <p>
      Some people choose to use the first Qualifying Round to practice and get familiar with the
      holes and the wind that they'll see for future rounds. They dial in each shot (spin, wind
      adjustment, etc.) but either forfeit or intentionally miss putts so that their score will
      not advance them to the next round.
    </p>
  </div>

  <div class="col-md-4 col-sm-12">
    <img src="/img/golfclash/tournaments/rules/Opening.png" class="img-responsive pad-16">
    <p>
      The second round will consist of the same 9 holes from the Qualifying Round but you'll need
      to play each hole twice for a total of 18 holes. This round is typically played on Thursday
      and Friday so you'll have 2 days to complete it. The bracket size for this round is 100
      people with the top 50 scores advancing. Unlike the Qualifying Round, you only get one chance
      to advance so if you end up in the bottom 50, your tournament is over and you'll leave without
      a prize. On the bright side, you will get a banner below your profile picture to show you made
      it to the Opening Round.
    </p>
  </div>

  <div class="col-md-4 col-sm-12">
    <img src="/img/golfclash/tournaments/rules/Weekend.png" class="img-responsive pad-16">
    <p>
      The Weekend Round structure is the same as the Opening Round in that it consists of 18 holes
      (1 set of 9 holes played twice) and takes place over the course of 2 days, usually Saturday
      and Sunday. Your 99 opponents in this round will likely be stiffer competition so it's time
      to focus and get low scores.
    </p>
    <p>
      The best part is that no matter what position you finish in, you'll be getting a prize chest,
      balls and coins. Of course finishing higher will lead to more chest cards, more (and often
      better) balls, and larger amounts of coins. And to top it off, you'll get a snazzy banner to
      show that you made it all the way to the weekend. If you manage to finish in the top 3, that
      banner will have either gold, silver or bronze edges that will instantly strike fear into all
      your opponents.
    </p>
  </div>

  <div class="col-lg-12"><hr></div>

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <a id="ChoosingTournamentLevel"></a>
    <h4>Choosing Tournament Level</h4>
    <p>
      The tournament levels you <strong><em>can</em></strong> enter may be different than the
      levels you <strong><em>should</em></strong> enter. The first consideration should be your
      bankroll. A reasonable criteria is to have at least 10x the tournament entry fee. That means
      to enter the Expert tournament, which has a $1,000,000 entry fee, you should have a minimum
      of $10,000,000 in the bank.
    </p>
    <p>
      On top of the entry fee, you also need to pay attention to the cost <em>per match</em>. If
      you make it through the entire tournament you'll end up playing 45 matches. It's unlikely
      that you would lose all of them unless you're totally out of your league but you may want to
      play it conservatively. A very safe strategy would be to assume you'll only win 25% of your
      matches. In that scenario, you would lose almost 25x the match fee. At the Pro level, that
      would mean losing $250,000 (at $10,000 per match) on top of your tournament entry fee. Again,
      a 25% win rate is a very conservative number. Adjust it to your liking. Decide for yourself
      if you're able to handle that and go from there.
    </p>
    <p>
      Lastly, you need to decide how good of a chance you have to make it to the weekend round.
      That should be your minimum goal since that's where prizes are awarded. If you struggled to
      do that in your last tournament or missed the weekend round all together, consider dropping
      down on your next entry. If you're consistently making top 20 finishes then you may be ready
      to step up to the next level and take advantage of the bigger prizes.
    </p>

    <a id="Tiebreakers"></a>
    <h4>Understanding Tiebreakers</h4>
    <p>
      You can find the official tiebreaking rules on the <a href="https://playdemic.helpshift.com/a/golf-clash/?l=en&s=tournaments&f=what-happens-if-i-have-the-same-score-as-someone-else-in-my-tournament&p=all" target="_blank" rel="noopener">Official Playdemic site.</a> It will break down what tiebreakers are used in
      each round throughout the tournament! Here's the quoted text from their website:
    </p>
    <blockquote class="text-normal-size">
      <p>
        <h4>Qualification round</h4>
        <ol>
          <li>Number of completed tournament matches. The more matches completed during qualification the better.</li>
          <li>Number of 'Impressive Scores'. We will compare the number of Albatrosses, then Eagles, then Birdies and then Pars that each player scored in the Qualification Round.</li>
          <li>Players that are still tied after these rules will share the position.</li>
        </ol>
      </p>
        <h4>Opening round</h4>
        <ol>
          <li>Qualifying Round scores will be compared - lower the better.</li>
          <li>Number of completed tournament matches. The more matches completed during the tournament the better.</li>
          <li>Number of 'Impressive Scores'. We will compare the number of Albatrosses, then Eagles, then Birdies and then Pars that each player scored in the Opening Round.</li>
          <li>Number of 'Impressive Scores'. We will compare the number of Albatrosses, then Eagles, then Birdies and then Pars that each player scored in the Qualification Round.</li>
          <li>Players that are still tied after these rules will share the position.</li>
        </ol>
      <p>
        <h4>Weekend round</h4>
        <ol>
          <li>Opening Round scores will be compared - lower the better.</li>
          <li>Qualifying Round scores will be compared - lower the better.</li>
          <li>Number of completed tournament matches. The more matches completed during the tournament the better.</li>
          <li>Number of 'Impressive Scores'. We will compare the number of Albatrosses, then Eagles, then Birdies and then Pars that each player scored in the Weekend Round.</li>
          <li>Number of 'Impressive Scores'. We will compare the number of Albatrosses, then Eagles, then Birdies and then Pars that each player scored in the Opening Round.</li>
          <li>Number of 'Impressive Scores'. We will compare the number of Albatrosses, then Eagles, then Birdies and then Pars that each player scored in the Qualification Round.</li>
          <li>Players that are still tied after these rules will share the position and will receive the same prize.</li>
        </ol>
      </p>
      <footer><a href="https://playdemic.helpshift.com/a/golf-clash/?l=en&s=tournaments&f=what-happens-if-i-have-the-same-score-as-someone-else-in-my-tournament&p=all" target="_blank" rel="noopener">Official Playdemic site.</a></footer>
    </blockquote>
    <p>
      At any time during your tournament play you'll be able to see any tiebreakers in use by
      clicking on the equals icon
      <img src="/img/golfclash/tournaments/rules/Tiebreaker.png" style="width: 24px; height: 24px;">
      on your leaderboard, which will explain why one player is ranked above the other.
    </p>

  </div>

</div>

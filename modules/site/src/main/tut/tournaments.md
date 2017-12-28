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
        {{ tag[1].date }}:{{ tag[0] }}
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
          <img class="img-responsive img-center" src="/img/golfclash/tournaments/titles/{{ key }}.png">
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
      and Friday so you'll have 2 days to complete it, although it's usually better to finish
      quickly (see <a href="#Tiebreakers">Tiebreakers</a>). The bracket size for this round is 100
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
      play it safe. A very safe strategy would be to assume you'll only win 25% of your matches.
      In that scenario, you would lose almost 25x the match fee. At the Pro level, that would mean
      losing $250,000 (at $10,000 per match) on top of your tournament entry fee. Again, a 25% win
      rate is a very conservative number. Adjust it to your liking. Decide for yourself if you're
      able to handle that and go from there.
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
      There are only 3 tiebreakers used throughout the tournament. The first tiebreaker used is
      cumulative score for all rounds completed before the current round. That means if you and an
      opponent score the same score in the Opening Round, the person with the lowest score in the
      Qualifying Round would win the tiebreaker. In the Weekend Round, both the Qualifying Round
      and Opening Round score added together would determine who wins.
    </p>
    <p>
      If 2 players have the same cumulative score, then the next tiebreaker is the player who has
      completed the most holes at that point. This is why it's important to
      <strong><em>always</em></strong> finish your rounds.
    </p>
    <p>
      The last tiebreaker used in situations where both players have the same cumulative score and
      same number of holes played, is the player who finished their holes first. Some strategies
      are available to try and overcome this somewhat arbitrary rule.
      <dl>
        <dt>Waiting to Open the App Until You're Ready to Play.</dt>
        <dd>
          When you advance to the next round, you'll be placed in a bracket the next time you open
          the app after the round has started. It doesn't matter if you actually do anything in the
          app. Just opening it will place you in a bracket and some of your opponents may be
          playing immediately. By waiting until you're ready, you'll be one of the first to finish.
        </dd>
        <dt>Forfeiting Shootouts</dt>
        <dd>
          Not spending time to play shootouts will lead to a quicker round and finishing before
          players who choose to play them.
        </dd>
      </dl>
    </p>
    <p>
      At any time during your tournament play you'll be able to see any tiebreakers in use by
      clicking on the equals icon
      <img src="/img/golfclash/tournaments/rules/Tiebreaker.png" style="width: 24px; height: 24px;">
      on your leaderboard, which will explain why one player is ranked above the other.
    </p>

  </div>

</div>

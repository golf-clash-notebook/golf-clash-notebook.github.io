---
layout: gcn-layout
title:  "Overlay Post Mortem"
section: "overlay-post-mortem"
permalink: /overlay-post-mortem/
---

## GCN Overlay Post Mortem
##### 27 APR 2019

I think an explanation is warranted for the overlay feature removal. I've seen a bit of false
information and/or speculation being spread around the online community so this is my attempt to
give you some facts on what happened and why it happened.

### The Die is Cast

Early this year, we were engaged by Playdemic [PD], the creators/maintainers of Golf Clash, to start
a discussion on removing overlay capabilities from our Android and iOS apps in order to create a
'Safe and Fair' gaming environment as they envisioned it. While we were not thrilled with the
direction PD was choosing to steer the game, we were willing to work with them, as requested,
to help provide input to arrive at the best possible solution for everyone involved, given the
underlying goals set in place.

Over the next few months, we exchanged dozens of emails ironing out specifics (or at least as
specific as was allowed), on what capabilities were to be removed and the time frame for all of
this to occur. After some back and forth, we reached an agreement on what the next few weeks
leading up to the ban would/should look like.

### Rollout FUBAR

This past week (22 APR through 26 APR), has seen that plan executed on our part, and subsequently
burned to the ground by Playdemic. Bear in mind, this was an almost impossible situation to come
out clean on the other end given perfect conditions, but the series of events that have unfolded
have turned it into an exercise in futility.

As a bit of background, here's chronological list of relevant events.

|  Date  | Event |
| ------ | --- |
| 31 JAN | Initial e-mail received about upcoming overlay ban. |
| 07 MAR | Teleconference call w/ PD to discuss changes/timeline. |
| 07 MAR | PD gives the OK to disclose details on the ban publicly. |
| 07 MAR | Dedicated e-mail chain to maintain contact about upcoming changes/requirements. |
| 07 MAR | April 8th deadline is requested by PD. |
| 07 MAR | **PD confirms they've reached out to all other 3rd party app developers to plan for April 8th deadline and that 'everyone has received our proposed changed extremely well.'.** |
| 08 MAR | Request for ruling on iOS multitasking is sent to PD. |
| 13 MAR | **PD decides that iOS widget/multitasking capabilities are to be removed.** |
| 28 MAR | PD moves deadline to April 22nd to accommodate other developer(s). |
| 10 APR | PD receives a GCN iOS test build with requested changes in place. |
| 10 APR | PD receives a GCN Android test build with requested changes in place. |
| 16 APR | **PD confirms iOS build meets requirements for April 22nd deadline.** |
| 22 APR | Android/iOS builds are released to the public. |
| 23 APR | **PD notifies us that they are still in discussions with other developers who are not currently required to make changes for the agreed upon April 22nd deadline.** |
| 26 APR | **PD notifies us they're reconsidering their stance on iOS multitasking features.** |
| 29 APR | PD reconfirms that iOS multitasking will not be allowed. |

<br>

> <sup>The long gap from 31 JAN until 7 MAR saw multiple email exchanges and a few requests from PD to postpone the meeting.</sup>

> <sup>I've added emphasis to the events that are most important as I see them.</sup>

### Deadlines and Moving Targets

Long story short, we were led to believe for the better part of 2 months that all developers were
contacted, and either on board with the new requirements, or willing to have their app become
non-functional in some capacity once the April 22nd deadline came. As it turns out, this was not
the case at all.

The primary issue here, if it's not already clear, is the timing of the last 3 events in the above
list. We were notified ***AFTER*** we complied with the agreed upon deadline that the deadline had
been moved. **I'll say it again, we were told AFTER the deadline had come and gone, that the agreed
upon deadline was no longer valid.** PD has privately expressed remorse for their role in the
situation and gratitude for our willingness to work with them which is all well and good, but I
would trade them both for clarity and accountability.

On top of this, 3 days later we were told that the requirements of the overlay ban could
now potentially be changed. For anyone in the software, construction or any industry where you
build anything according to specifications and deadlines, imagine for a second what the
repercussions might be if this happened in your job.

We are now being told that PD is now reconsidering the ban on iOS multitasking capabilities due to
the ease of which Android users can use 3rd party apps to allow split views. **I raised this EXACT
point in our teleconference on 7 MAR. PD acknowledged this point. PD deliberated on this point. PD
then ruled that multitasking would not be allowed.** For reasons I can't even fathom, this point is
now up in the air yet again. I've already responded to PD, trying to reason with them that the
'ease' they refer to is entirely dependant on the device manufactuer, OS version event the country
where you live. Legislating each and every one of these variable is a very slippery slope that
will only further complicate an already complicated situation. Regardless of the outcome, either
the requirements will stay as we were initially told, or the requirements will need to change on
the Android side.

This line of work is not unlike most others. It demands requirements, specificity and precision to
do well. Without those, you get botched releases, regressions, lost data or worse. This type of
scenario is one that I work hard to avoid both in this project and my full time job. While some
level of this chaos was probably inevitable, it has been exacerbated to such a degree by
carelessness and miscommunication that I'm actually skeptical if it can or will ever be fixed. The
fact that this situation was allowed to happen is staggering.

### A Rock and A Hard Place

Another outcome of this boondoggle is that Android app updates have now been flagged by Google for
an unrelated reason that has previously been dealt with in the past. I can only guess that this is
somehow related to the epic flood of 1 star reviews due to the overlay removal. I'm working to
resolve the issue but even if I wanted to restore the overlay functionality today, I couldn't
because I'm waiting on clarification from Google. So now the store listing has screenshots of the
old functionality that doesn't presently exist, and I can't update the information to accurately
reflect the current capabilities of the app. However, thank you for the outpour of support from
the community in sharing your views on the app. I would never ask anyone to do that, but it's
certainly appreciated.

As for my future in terms of Golf Clash Notebook, the app, overlays, etc. I'm not confident
on how any of this turns out. We'll continue working with Playdemic so they can get the game and
the apps to a state where they want it, although I'm no longer sure what that that vision is. I
hope to share some new information on this in the coming week(s).

As for the website/app, I'll continue making the typical content updates for tournaments and
courses for the near future out of a sense of obligation to those people who use the app for the
content organization/search. I make no promises for my involvement in the community for the long
term. Ultimately at this point, I've lost interest in the game for a number or reasons and that
interest has always the #1 driving factor in building all the things that I have over the past
year and a half. The amount of nonsense being inflicted simply isn't worth enduring any longer.

I will try to answer any questions in the Golf Clash Notebook Facebook page post that this is
linked to. I will also update this post with any new information and necessary clarifications.

Best of luck to you all.

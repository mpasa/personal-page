---
title: Loren ipsum dolor sit amet
permalink: play-new-validation-api
published: 2018-06-10
tags:
    - scala
    - play
    - web
---

> Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo 

## Math is cool

$$$
\sum_{i=1}^{100} i \times 3 
$$$

## Code is cooler

Code is rendered using `highlight.js` with a cool theme.

```scala
trait Mapping[T] {
  def bind(data: Map[String, String]): Either[Seq[FormError], T]
  def unbind(value: T): (Map[String, String], Seq[FormError])
}
```

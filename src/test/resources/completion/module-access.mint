module Module {
  fun containsMaybe (
    maybeElement : Maybe(Dom.Element)
  ) : Bool {
    maybeElement
    |> Ma<caret>.func()
  }
}

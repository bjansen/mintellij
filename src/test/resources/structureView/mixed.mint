component SameName {
    fun func(foo: Html) : Html {
        <div/>
    }
    fun render() : Html {
        <div/>
    }
}

record SameName4 {
    foo: Html
}

store SameName2 {
    fun foo : String {
        ""
    }
}

module SameName3 {
    fun foo : String {
        ""
    }
}

enum SameName {
    Element
}


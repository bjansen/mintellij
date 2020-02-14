routes {
  / {
    Application.setPage("index")
  }

  /users/:id (id: Number) {
    sequence {
      Application.setPage("show")
      Application.loadUser(id)
    }
  }
}
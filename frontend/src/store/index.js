import Vue from 'vue'

const state = Vue.observable({
  user: null,
  isLoggedIn: false
})

const store = {
  get user() {
    return state.user
  },
  get isLoggedIn() {
    return state.isLoggedIn
  },
  get userId() {
    return state.user ? state.user.id : null
  },
  get role() {
    return state.user ? state.user.role : null
  },

  login(user) {
    state.user = user
    state.isLoggedIn = true
    sessionStorage.setItem('jobplus_user', JSON.stringify(user))
  },

  logout() {
    state.user = null
    state.isLoggedIn = false
    sessionStorage.removeItem('jobplus_user')
  },

  setUser(user) {
    if (user) {
      state.user = user
      state.isLoggedIn = true
    } else {
      state.user = null
      state.isLoggedIn = false
    }
  }
}

export default store

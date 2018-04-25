  var el = null;
  function router () {
    // Lazy load view element:
    el = el || document.getElementById('view');
      // Current route url (getting rid of '#' in hash as well):
      var url = location.hash.slice(1) || '/';
      if(url == "/user/login")
    	  login("admin@gmail.com","welcome");
      //el.innerHTML = tmpl(route.templateId, new route.controller());
  }
  // Listen on hash change:
  window.addEventListener('hashchange', router);
  // Listen on page load:
  window.addEventListener('load', router);
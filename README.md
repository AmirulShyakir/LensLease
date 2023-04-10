# LensLease

Adriel | Amirul | Dekai | Jonathan | Junwei | Leeann

To either host online or expose web services and use that to deploy on localhost

## To do list
### Ban 
- [X] BanRequest entity should have booking attribute (so admin can see which booking was this ban request for service from and see the User booking.customer that is the complainant) //Jon/Adriel
- [X] Banned services should not be visible on landing page //Jon/Adriel
- [x] Banned users should not be able to book services in checkout.xhtml //Amirul 
- [x] Banned services should not be bookable in checkout.xhtml //Amirul 
- [x] Services which provider is banned should not be bookable in checkout.xhtml //Amirul 
- [ ] Banned users should not be able to accept requests // Jun Wei
- [x] Ban request for service function as a button "Report service" in individualServicePage.xhtml //Amirul
- [x] Ban request for user function as a button "Report user" within the card in myBookings.xhtml -> All bookings (only for CONFIRMED and TORATE and COMPLETED status)

### Admin 
- [X] Dashboard tiles to show some useful information //Jon / Adriel
- [X] adminUsers.xhtml // Jon / Adriel 
- [X] Admin Log in page design //Jon / Adriel
- [X] Ban modal should have "reject" button. consider adding isRejected attribute in Banrequest entity //Jon / Adriel

### Landing page
- [X] Data init different pictures for services (try different sizes and see if if messes up landing page) //Jon / Adriel
- [X] welcome page for all users should be homePage (logged out version of landingPage.xhtml). header/navigation bar for logged out users to only have sign in option that leads to login page. logged out users upon clicking any service should have popup alert to tell them to log in //Jon / Adriel

### Bookings and Services
- [x] Users should not be able to book their own services //Amirul
- [x] Cancel booking as user (only for PENDING AND CONFIRMED) //Amirul
- [x] Cards for services in myServices.xhtml //Jun Wei 
- [x] addNewService page or modal //Jun Wei / Leeann
- [x] addNewService should have some basic input validation so user cannot create an empty service
- [] addPortfolio should have some basic input validation so user cannot create an invalid portfolio link //Jun Wei

### Forum 
- [ ] Add forum topics // Dekai
- [ ] Add forum reply // Dekai
- [ ] Add link to Forumpage in defaultLandingPage.xhtml //Dekai

### Account 
- [x] Portfolio //Leeann
- [x] Edit Account //Junwei
- [x] signUp should have some basic input validation so user cannot create an empty service// Jun Wei

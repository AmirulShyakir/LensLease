# LensLease

Adriel | Amirul | Dekai | Jonathan | Junwei | Leeann

To either host online or expose web services and use that to deploy on localhost

## To do list
### Ban 
- [ ] BanRequest entity should have booking attribute (so admin can see which booking was this ban request for service from and see the User booking.customer that is the complainant) //Jon/Adriel
- [ ] Banned services should not be visible on landing page //Jon/Adriel
- [x] Banned users should not be able to book services in checkout.xhtml //Amirul 
- [x] Banned services should not be bookable in checkout.xhtml //Amirul 
- [x] Services which provider is banned should not be bookable in checkout.xhtml //Amirul 
- [ ] Banned users should not be able to accept requests // Jun Wei
- [x] Ban request for service function as a button "Report service" in individualServicePage.xhtml //Amirul
- [ ] Ban request for user function as a button "Report user" within the card in myBookings.xhtml -> All bookings (only for CONFIRMED and TORATE and COMPLETED status)

### Admin 
- [ ] Dashboard tiles to show some useful information //Jon / Adriel
- [ ] adminUsers.xhtml // Jon / Adriel 
- [ ] Admin Log in page design //Jon / Adriel
- [ ] Ban modal should have "reject" button. consider adding isRejected attribute in Banrequest entity //Jon / Adriel

### Landing page
- [ ] Data init different pictures for services (try different sizes and see if if messes up landing page) //Jon / Adriel
- [ ] welcome page for all users should be homePage (logged out version of landingPage.xhtml). header/navigation bar for logged out users to only have sign in option that leads to login page. logged out users upon clicking any service should have popup alert to tell them to log in //Jon / Adriel

### Bookings and Services
- [x] Users should not be able to book their own services //Amirul
- [x] Cancel booking as user (only for PENDING AND CONFIRMED) //Amirul
- [x] Cards for services in myServices.xhtml //Jun Wei 
- [x] addNewService page or modal //Jun Wei / Leeann
- [ ] search function in myServices.xhtml and myBookings.xhtml... can scrap? //Leeann

### Forum 
- [ ] Add forum topics // Dekai
- [ ] Add forum reply // Dekai

### Account 
- [ ] Portfolio //Leeann
- [x] Edit Account //Junwei

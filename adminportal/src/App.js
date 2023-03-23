import logo from './logo.svg';
import './App.css';
import { Routes, Route } from "react-router-dom";
import NavBar from "./components/NavBar/NavBar";
import Sidebar from "./components/Sidebar";
import Footer from "./components/Footer";
import Index from "./components/Index";
import ManageUser from './containers/ManageUser';
import SearchUsers from './containers/SearchUsers';

function App() {
  return (
    <div className="wrapper">
    <NavBar />
    <Sidebar />
    <div className="content-wrapper">
      <Routes>
        {/* <Route path="/manageCustomer/:id" element={<ManageCustomer />} 
        /> */}
        <Route path="/manageCustomer" element={<ManageUser />} />
        <Route path="/searchCustomers" element={<SearchUsers />} />
        <Route path="/" element={<Index />} />
      </ Routes >
    </div>
    <Footer />
    </div>
  );
}

export default App;

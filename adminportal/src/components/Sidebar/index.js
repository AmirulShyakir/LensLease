import React from "react";
import { Link } from 'react-router-dom';

function Sidebar() {
 return (
 <aside className="main-sidebar sidebar-dark-primary elevation-4">
    <Link to="/" className="brand-link">
        <img
            src="logo.png"
            alt=" Logo"
            // className="brand-image img-circle elevation-3"
            // style={{ opacity: ".8" }}
        />
        <span className="brand-text font-weight-light">
        <b>Simple</b> Admin
        </span>
    </Link>
    <div className="sidebar">
        <nav className="mt-2">
            <ul
            className="nav nav-pills nav-sidebar flex-column"
            data-widget="treeview"
            role="menu"
            data-accordion="false"
            >
            <li className="nav-item">
                <Link to="/manageCustomer" className="nav-link">
                    <i className="nav-icon fa fa-plus-circle" />
                    <p>Create New Users</p>
                </Link>
            </li>
            <li className="nav-item">
                <Link to="/searchCustomers" className="nav-link">
                    <i className="nav-icon fa fa-users" />
                    <p>List Users</p>
                </Link>
            </li>
            </ul>
        </nav>
    </div>
 </aside>
 );
}
export default Sidebar;

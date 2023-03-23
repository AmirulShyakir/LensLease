import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Modal from "react-responsive-modal";
import moment from "moment";
import DataTable from "react-data-table-component";
 
import Api from "../../helpers/Api";
import "react-responsive-modal/styles.css"; //Import css
import ContentHeader from "../../components/ContentHeader";
 
function SearchUsers(props) {
  const [data, setData] = useState([]);
  const [selectedCustomerId, setSelectedCustomerId] = useState(-1);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  // const [fieldName, setFieldName] = useState("");
  // const [fieldValue, setFieldValue] = useState("");
  // const [contactType, setContactType] = useState("PHONE");
  // const [contactValue, setContactValue] = useState("");
 
  useEffect(() => {
    reloadData();
  }, []);
 
  const reloadData = () => {
    Api.getAllUsers()
      .then((res) => res.json())
      .then((customers) => {
        for (const customer of customers) {
          const { gender, dob, created } = customer;
 
          if (gender === 1) {
            customer.gender = "Female";
          } else if (gender === 2) {
            customer.gender = "Male";
          }
 
          //remove [UTC] suffix
          customer.dob = dob.substring(0, dob.length - 5);
          customer.created = created.substring(0, created.length - 5);
        }
 
        setData(customers);
      });
  };
 
  // const handleDeleteCustomer = (cId) => {
  //   if (window.confirm(`Do you want to delete customer?`)) {
  //     Api.deleteCustomer(cId).then(() => {
  //       reloadData();
  //     });
  //   }
  // };
 
  // const handleDeleteField = (cId, fId) => {
  //   if (window.confirm(`Do you want to delete field?`)) {
  //     Api.deleteField(cId, fId).then(() => {
  //       console.log("###deleted field");
  //       reloadData();
  //     });
  //   }
  // };
 
  // const handleDeleteContact = (contactId) => {
  //   if (window.confirm(`Do you want to delete contact?`)) {
  //     Api.deleteContact(contactId).then(() => {
  //       reloadData();
  //     });
  //   }
  // };
 
  // const handleAddContact = (cId) => {
  //   const contactValueTrimmed = contactValue.trim();
 
  //   if (contactValueTrimmed.length <= 0) {
  //     return;
  //   }
 
  //   const filteredData = {};
 
  //   if (contactType === "PHONE") {
  //     filteredData.phone = contactValueTrimmed;
  //   } else if (contactType === "EMAIL") {
  //     filteredData.email = contactValueTrimmed;
  //   } else {
  //     return;
  //   }
 
  //   Api.addContact(cId, filteredData).then(() => {
  //     setContactValue("");
  //     reloadData();
  //   });
  // };
 
  // const handleAddField = (cId) => {
  //   const fieldNameTrimmed = fieldName.trim();
  //   const fieldValueTrimmed = fieldValue.trim();
 
  //   if (fieldNameTrimmed.length === 0 || fieldValueTrimmed.length === 0) {
  //     return;
  //   }
 
  //   const filteredData = {
  //     name: fieldNameTrimmed,
  //     fieldValue: fieldValueTrimmed,
  //   };
 
  //   Api.addField(cId, filteredData).then(() => {
  //     setFieldName("");
  //     setFieldValue("");
 
  //     reloadData();
  //   });
  // };
 
  const renderModalBody = () => {
    if (selectedCustomerId >= 0) {
      // let selectedCustomer = null;
      // for (const customer of data) {
      //   if (customer.id === selectedCustomerId) {
      //     selectedCustomer = customer;
      //   }
      // }
 
      // const fields = selectedCustomer.fields.map((item) => {
      //   const { id, name, fieldValue } = item;
 
      //   return (
      //     <div key={id} className="row col-12">
      //       <div className="col-4 cap-field">
      //         <b>{name}</b>
      //       </div>
      //       <div className="col-4">{fieldValue}</div>
      //       <div className="col-4">
      //         <button
      //           className="btn btn-danger"
      //           onClick={() => handleDeleteField(selectedCustomer.id, id)}
      //         >
      //           Delete
      //         </button>
      //       </div>
      //     </div>
      //   );
      // });
 
      // const contacts = selectedCustomer.contacts.map((item) => {
      //   const { id, phone, email } = item;
      //   let label = "";
      //   let value = "";
 
      //   if (phone) {
      //     label = "Phone";
      //     value = phone;
      //   } else if (item["email"]) {
      //     label = "Email";
      //     value = email;
      //   }
 
      //   return (
      //     <div key={id} className="row col-12">
      //       <div className="col-4">
      //         <b>{label}</b>
      //       </div>
      //       <div className="col-4">{value}</div>
      //       <div className="col-4">
      //         <button
      //           className="btn btn-danger"
      //           onClick={() => handleDeleteContact(id)}
      //         >
      //           Delete
      //         </button>
      //       </div>
      //     </div>
      //   );
      // });
 
      // const addFieldComponent = (
      //   <div className="row col-12">
      //     <div className="col-4">
      //       <input
      //         className="form-control"
      //         id="inputField"
      //         placeholder="field name"
      //         value={fieldName}
      //         onChange={(e) => setFieldName(e.target.value)}
      //         required
      //       />
      //     </div>
      //     <div className="col-4">
      //       <input
      //         className="form-control"
      //         id="inputFieldValue"
      //         placeholder="field value"
      //         value={fieldValue}
      //         onChange={(e) => setFieldValue(e.target.value)}
      //         required
      //       />
      //     </div>
      //     <div className="col-4">
      //       <button
      //         className="btn btn-primary"
      //         onClick={() => handleAddField(selectedCustomer.id)}
      //       >
      //         Add Field
      //       </button>
      //     </div>
      //   </div>
      // );
 
      // const addContactComponent = (
      //   <div className="row col-12">
      //     <div className="col-4">
      //       <select
      //         className="form-control"
      //         id="selectContact"
      //         value={contactType}
      //         onChange={(e) => setContactType(e.target.value)}
      //       >
      //         <option value="PHONE">Phone</option>
      //         <option value="EMAIL">Email</option>
      //       </select>
      //     </div>
      //     <div className="col-4">
      //       <input
      //         className="form-control"
      //         id="inputContactValue"
      //         value={contactValue}
      //         onChange={(e) => setContactValue(e.target.value)}
      //         required
      //       />
      //     </div>
      //     <div className="col-4">
      //       <button
      //         className="btn btn-success"
      //         onClick={() => handleAddContact(selectedCustomer.id)}
      //       >
      //         Add Contact
      //       </button>
      //     </div>
      //   </div>
      // );
 
      const body = (
        <div className="row">
          {/* <div className="col-4">
            <b>Id</b>
          </div>
          <div className="col-8">{selectedCustomer.id}</div>
          <div className="col-4">
            <b>Name</b>
          </div>
          <div className="col-8">{selectedCustomer.name}</div>
          <div className="col-4">
            <b>Gender</b>
          </div>
          <div className="col-8">{selectedCustomer.gender}</div>
          <div className="col-4">
            <b>DOB</b>
          </div>
          <div className="col-8">
            {moment(selectedCustomer.dob).format("DD/MM/YYYY")}
          </div>
          <div className="col-4">
            <b>Created</b>
          </div>
          <div className="col-8">
            {moment(selectedCustomer.created).format("DD/MM/YYYY hh:mm:ss a")}
          </div>
          {fields}
          {contacts}
          {addFieldComponent}
          {addContactComponent} */}
        </div>
      );
 
      return body;
    } else {
      return "";
    }
  };
 
  const showModal = (customerId) => {
    console.log("###showModal : ", customerId);
    setSelectedCustomerId(customerId);
    setModalIsOpen(true);
  };
 
  const closeModal = () => {
    setModalIsOpen(false);
  };
 
  const columns = [
    {
      name: "Id",
      grow: 0.3,
      sortable: true,
      selector: (customer) => customer.id,
    },
    {
      name: "Name",
      sortable: true,
      selector: (customer) => customer.name,
    },
    {
      name: "Gender",
      grow: 0.3,
      sortable: true,
      selector: (customer) => customer.gender,
    },
    {
      name: "DOB",
      sortable: true,
      selector: (customer) => moment(customer.dob).format("DD/MM/YYYY"),
    },
    {
      name: "Created",
      sortable: true,
      selector: (customer) => moment(customer.created).format("DD/MM/YYYY"),
    },
    {
      name: "",
      button: true,
      width: "150px",
      cell: ({ id }) => {
        return (
          <>
            <button
              data-type="view"
              className="search btn btn-default"
              onClick={() => showModal(id)}
            >
              <i className="fa fa-fw fa-search" />
            </button>
            <Link to={`/manageCustomer/${id}`}>
              <button data-type="edit" className="btn btn-default">
                <i className="fa fa-fw fa-edit" />
              </button>
            </Link>
            <button
              data-type="delete"
              className="btn btn-default"
              // onClick={() => handleDeleteCustomer(id)}
            >
              <i className="fa fa-fw fa-trash" />
            </button>
          </>
        );
      },
    },
  ];
 
  return (
    <>
      <ContentHeader
        title="Search Users"
        links={[
          {
            to: "/",
            label: "Home",
          },
          {
            label: "List Customers",
            isActive: true,
          },
        ]}
      />
      <section className="content" key="content">
        <div className="row">
          <div className="col-12">
            <div className="card">
              <div className="card-header">
                <h3 className="card-title">Search Users</h3>
              </div>
              <div className="card-body">
                <DataTable
                  columns={columns}
                  data={data}
                  highlightOnHover
                  pagination
                  paginationPerPage={5}
                  paginationRowsPerPageOptions={[5, 10, 50]}
                  sortable
                />
              </div>
            </div>
          </div>
        </div>
      </section>
      <Modal
        open={modalIsOpen}
        onClose={closeModal}
        contentLabel="Example Modal"
        key="modal"
        center
      >
        <div className="modal-header">
          <h2 className="modal-title">Customer Details</h2>
        </div>
        <div className="modal-body">{renderModalBody()}</div>
        <div className="modal-footer">
          <button className="btn btn-default pull-right" onClick={closeModal}>
            Close
          </button>
        </div>
      </Modal>
    </>
  );
}
 
export default SearchUsers;
import React, { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import DatePicker from "react-datepicker";
import Api from "../../helpers/Api";
import moment from "moment";
 
import "react-datepicker/dist/react-datepicker.css";
import ContentHeader from "../../components/ContentHeader";
 
function ManageUser(props) {
  const { id = 0 } = useParams();
  const [name, setName] = useState("");
  const [gender, setGender] = useState(1);
  const [dob, setDob] = useState(moment("1990-01-01 00:00:00").toDate());
  const navigate = useNavigate();
 
  //load for the edit case
  useEffect(() => {
    if (id) {
      Api.getUser(id)
        .then((res) => res.json())
        .then((customer) => {
          const { name, dob, gender } = customer;
          setName(name);
          setGender(gender);
          setDob(moment(dob, "YYYY-MM-DDTHH:mm:ssZ[UTC]").toDate());
        });
    }
  }, [id]);
 
  const validateData = () => {
    return name.trim().length > 0;
  };
 
  const handleSubmit = (e) => {
    //prevent the normal form submit
    e.preventDefault();
 
    // if (id === 0) {
      //create case
      if (validateData()) {
        Api.createCustomer({
          id,
          name,
          gender,
          dob,
        }).then((data) => {
          navigate("/searchCustomers");
        });
      }
    // } else {
    //   //edit case
    //   if (validateData()) {
    //     Api.updateCustomer(id, {
    //       name,
    //       gender,
    //       dob,
    //     }).then((data) => {
    //       navigate("/searchCustomers");
    //     });
    //   }
    // }
  };
 
  const headerLabel = "Create New User" ;
 
  return (
    <>
      <ContentHeader
        title="Manage Users"
        links={[
          {
            to: "/",
            label: "Home",
          },
          {
            label: headerLabel,
            isActive: true,
          },
        ]}
      />
      <section className="content" key="content">
        <div className="card card-primary">
          <div className="card-header">
            <h3 className="card-title">{headerLabel}</h3>
          </div>
          <form onSubmit={handleSubmit}>
            <div className="card-body">
              <div className="form-group">
                <label htmlFor="inputName">Name</label>
                <input
                  type="text"
                  id="inputName"
                  required
                  className="form-control"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                />
              </div>
              <div className="form-group">
                <label htmlFor="inputGender">Gender</label>
                <select
                  id="inputGender"
                  className="form-control"
                  value={gender}
                  onChange={(e) => setGender(e.target.value)}
                >
                  <option value="1">Female</option>
                  <option value="2">Male</option>
                </select>
              </div>
              <div className="form-group">
                <label htmlFor="inputName">Date of Birth (dd/mm/yyyy)</label>
                <div className="input-group">
                  <DatePicker
                    dateFormat="dd/MM/yyyy"
                    selected={dob}
                    onChange={(dob) => {
                      console.log("#dob: ", dob);
                      setDob(dob);
                    }}
                    customInput={<input className="form-control" />}
                  />
                </div>
              </div>
            </div>
            <div className="card-footer">
              <Link to="/searchCustomers">
                <button className="btn btn-default" type="button">
                  Cancel
                </button>
              </Link>
              <button className="btn btn-primary float-right" type="submit">
                Submit
              </button>
            </div>
          </form>
        </div>
      </section>
    </>
  );
}
 
export default ManageUser;
import { Button, Modal } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPhone,
  faEnvelope,
  faHouse
} from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";


function Salon({ salon }) {
  const navigate = useNavigate();
  const token = localStorage.getItem("jsonwebtoken");
  const [showModal, setShowModal] = useState(false);


  function showDetails() {
    if(token) {
      navigate(`/salons/${salon.id}`);
    } else {
      setShowModal(true);
    }
  }

  function goToLoginPage() {
    navigate("/login");
  }


  return (
    <section className="py-5 salonPage">
      <div className="container px-4 px-lg-5 mt-5">
        <div className="row gx-4 gx-lg-5 row-cols-1 row-cols-md-2 row-cols-lg-4">
          <div className="col mb-5">
            <div
              className="card h-100"
              style={{
                minWidth: "300px",
                maxWidth: "300px",
                height: "400px"
              }}
            >
              <img
                src={salon.photoUrl}
                alt="Salon"
                className="card-img-top"
                style={{
                  objectFit: "cover",
                  height: "150px",
                  borderTopLeftRadius: "10px",
                  borderTopRightRadius: "10px"
                }}
              />

              <div
                className="card-body p-4 d-flex flex-column justify-content-between"
                style={{ height: "200px" }}
              >
                <div className="text-center">
                  <h5 className="fw-bolder">{salon.name}</h5>

                  <p>
                    <FontAwesomeIcon icon={faPhone} /> {salon.phoneNumber}
                  </p>

                  <p>
                    <FontAwesomeIcon icon={faEnvelope} /> {salon.email}
                  </p>
                  <p>
                    <FontAwesomeIcon icon={faHouse} /> {salon.address}
                  </p>
                </div>
              </div>

              <div className="card-footer p-4 pt-0 border-top-0 bg-transparent">
                <div className="text-center">
                  <Button
                    variant="outline-dark"
                    onClick={showDetails}
                    className="mt-auto"
                  >
                    Detalii
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      {showModal && (
        <div
          className="modal show"
          style={{ display: 'block', position: 'center' }}
        >
          <Modal.Dialog>
            <Modal.Header closeButton onClick={() => setShowModal(false)}>
              <Modal.Title>Atentie!</Modal.Title>
            </Modal.Header>

            <Modal.Body>
              <p>Pentru a putea vedea detaliile salonului, este necesar sa te loghezi.</p>
            </Modal.Body>

            <Modal.Footer>
              <Button variant="dark" onClick={() => setShowModal(false)}>Inchide</Button>
              <Button variant="success" onClick={goToLoginPage}>Mergi catre pagina de logare.</Button>
            </Modal.Footer>
          </Modal.Dialog>
        </div>
      )}
    </section>
  );
}

export default Salon;

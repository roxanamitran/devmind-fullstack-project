import { Navbar, Nav, Container } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function NavBarComponent() {
  const token = localStorage.getItem("jsonwebtoken");
  const navigate = useNavigate();

  let role = null;
  if (token) {
    const decodedToken = jwtDecode(token);
    role = decodedToken.authorities[0];
  }

  function handleLogout(e) {
    e.preventDefault();
    localStorage.clear("jsonwebtoken");
    navigate("/");
  }

  return (
    <Navbar bg="dark" data-bs-theme="dark">
      <Container className="navBar">
        <Navbar.Brand>BeautyHub</Navbar.Brand>
        <Nav>
          <Nav.Link as={NavLink} to="/">
            Acasa
          </Nav.Link>
          {token && (
            <Nav.Link as={NavLink} to="/my_appointments">
              Vezi programarile tale
            </Nav.Link>
          )}
          {role === "ROLE_MANAGER" && (
            <Nav.Link as={NavLink} to="/my_salon">
              Salonul tau
            </Nav.Link>
          )}
          {!token && (
            <Nav.Link as={NavLink} to="/login">
              Logare
            </Nav.Link>
          )}
          {token && (
            <Nav.Link onClick={(e) => handleLogout(e)}>Delogare</Nav.Link>
          )}
        </Nav>
      </Container>
    </Navbar>
  );
}

export default NavBarComponent;

function FooterComponent() {
  return (
    <footer>
      <div className="footer-container">
        <div className="footer-section">
          <h4>
            Parerea ta este foarte importanta pentru noi! Ajuta-ne sa devenim
            mai buni.
          </h4>
          <p>
            Daca detii un salon si doresti sa faci parte din comunitatea
            noastra, nu ezita sa ne conactezi.
          </p>
        </div>
        <div className="footer-section">
          <h4>Contact</h4>
          <p>Email: info@yourcompany.com</p>
          <p>Phone: +123 456 789</p>
        </div>
      </div>
      <div className="footer-section">
        <h4>Follow Us</h4>
        <a href="https://facebook.com">Facebook</a>
        <a href="https://twitter.com">Twitter</a>
        <a href="https://instagram.com">Instagram</a>
      </div>
      <div className="footer-bottom">
        <p>
          &copy; {new Date().getFullYear()} Your Company. All rights reserved.
        </p>
      </div>
    </footer>
  );
}

export default FooterComponent;

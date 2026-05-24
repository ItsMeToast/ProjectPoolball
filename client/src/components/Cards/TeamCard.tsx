import "./TeamCard.css";
function TeamCard(props: any) {
  const styles = {
    overlay: {
      backgroundColor: "var(--" + props.code + "-main-opacity)",
      color: "var(--" + props.code + "-accent)",
    },
  };

  return (
    <div className="teamcard container text-center align-items-center px-0 mx-0">
      <img className="teamcard-logo no-interact" src={"../public/" + props.code + ".png"} />
      <div className="teamcard-overlay text-center position-absolute" style={styles.overlay}>
        <div className="teamcard-code">{props.code}</div>
        <div className="teamcard-name">{props.name}</div>
      </div>
    </div>
  );
}

export default TeamCard;

package packet;

/**
 * PacketHeaders are defined to identify what data a packet contains and
 * how should the server or client react to the data.
 * @author Joshua Preece
 * @version 1.0
 */
public enum PacketHeaders {
	unknown,
	questions,
	team,
	results,
	id,
	disconnect
}

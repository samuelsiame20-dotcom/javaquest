package quest.engine;

import static quest.engine.LevelRegistry.room;

import java.util.List;

/** World 3 - The Armory (robust Java). ICT261 Unit 4 (exceptions) and Unit 5 (generics and collections). */
final class World3Rooms {

    private static final String WORLD_3 = "World 3 - The Armory";

    private World3Rooms() {
    }

    static List<Level> rooms() {
        return List.of(
                room(11, WORLD_3, "w3", "Level11_CursedGold", "Cursed Gold",
                        """
                        The Armory's treasury runs on a ledger of loot codes and gold values, and a curse
                        has crept into it: garbled numbers, lines torn in half, the same gem listed twice.
                        The old importer died at the first cursed line and took the whole night's count
                        with it. Write one that skips and reports each bad line, keeps going, and always
                        shuts the ledger, even when the shelf collapses mid-read.
                        """,
                        "parseLine, addEntry, the two custom exceptions, importLedger and importFile. "
                                + "The skip-and-report policy is in the file.",
                        List.of(
                                "Three layers: parseLine throws for one bad line; importLedger catches per line "
                                        + "INSIDE the loop (so the import keeps going); importFile turns "
                                        + "'file not found' into your own checked exception.",
                                "Throw with 'throw new X(...)'; declare checked ones with 'throws'. A multi-catch "
                                        + "(catch (A | B e)) handles both bad-line kinds at once, and "
                                        + "NumberFormatException is already an IllegalArgumentException. "
                                        + "try (BufferedReader in = new BufferedReader(source)) closes the reader "
                                        + "however the block ends. Custom exceptions pass their message (and "
                                        + "cause) to super(...).",
                                """
                                parseLine:
                                  parts = line.split("\\\\|")
                                  not exactly 2 parts -> throw IAE
                                  value = Integer.parseInt(parts[1].trim())
                                  value outside 1..9999 -> throw IAE
                                importLedger:
                                  try (in = new BufferedReader(source)) {
                                    n = 0
                                    while ((line = in.readLine()) != null) {
                                      n++; skip blank lines
                                      try { addEntry(map, parseLine(line)) }
                                      catch (DuplicateLootException
                                             | IllegalArgumentException e) {
                                        problems.add("line " + n + ": " + msg)
                                      } } }
                                importFile:
                                  catch NoSuchFileException e ->
                                    throw new LedgerNotFoundException(msg, e)
                                """),
                        "ICT261 Unit 4 (exception handling): checked vs unchecked, throw vs throws, custom "
                                + "exceptions, multi-catch, try-with-resources, exception translation, and "
                                + "friendly messages for users vs technical causes for logs.",
                        100),

                room(12, WORLD_3, "w3", "Level12_BagOfHolding", "Bag of Holding",
                        """
                        The Quartermaster hands out Bags of Holding: bigger inside than out, but each bag
                        is enchanted to hold ONE kind of thing. Put a gem in a potion bag and the magic
                        spits it back out, before the bag is even sewn. That is generics: the compiler
                        checks the kind for you. Mend the bags, and teach them to pour into any container
                        that fits.
                        """,
                        "Gem.compareTo, the generic Inventory<T extends Item>, copyAll (PECS) and best "
                                + "(multiple bounds). The signatures are given: read them.",
                        List.of(
                                "Inside Inventory<T extends Item>, T is 'some kind of Item', so item.weight() "
                                        + "works. Keep the items in a List<T> field and the running weight in an int.",
                                "PECS: 'from' only gives items out (? extends T), 'to' only takes them in "
                                        + "(? super T), so a loop of to.add(item) compiles. For read-only views, "
                                        + "return List.copyOf(items). Integer.compare(a, b) is a safe compareTo. "
                                        + "Erasure: at runtime the bag forgets T, which is why you can't write "
                                        + "new T() or instanceof T.",
                                """
                                add:      if total + item.weight() > max: return false; else add, total += weight
                                heaviest: best = null; for each: if best == null or weight > best.weight: best = it
                                copyAll:  for (T item : from) to.add(item); return from.size()
                                best:     like heaviest, but compare with item.compareTo(best) > 0
                                """),
                        "ICT261 Unit 5 (generics): generic classes and methods, bounded types, multiple "
                                + "bounds (T extends A & B), wildcards and PECS, type erasure and its limits.",
                        100),

                room(13, WORLD_3, "w3", "Level13_MerchantsLedger", "The Merchant's Ledger",
                        """
                        In the Armory's market hall, merchants scribble sales into a shared ledger, and
                        squabble. Is this coin the same as that one? Which items have we sold? Who took the
                        most gold? Every question needs the right container: a list remembers order, a set
                        refuses duplicates, a map looks things up. Choose well, and let streams do the
                        counting.
                        """,
                        "Coin.equals and hashCode, then five ledger questions, each answered with the right "
                                + "collection or a stream.",
                        List.of(
                                "For each method, ask: do I need order? Duplicates? Look-up by key? Sorted keys? "
                                        + "The answer picks ArrayList, HashSet, LinkedHashSet, HashMap or TreeMap.",
                                "equals must check the type (getClass() or instanceof) before casting, then compare "
                                        + "realm and year; hashCode must agree with equals: Objects.hash(realm, "
                                        + "year). LinkedHashSet keeps first-seen order; TreeMap keeps keys sorted. "
                                        + "Streams: filter -> map -> distinct -> toList, and "
                                        + "collect(Collectors.groupingBy(key, Collectors.counting())).",
                                """
                                equals:        same object -> true; null or other class -> false;
                                               cast, then realm.equals(o.realm) && year == o.year
                                distinctCoins: new HashSet<>(purse).size()
                                uniqueItems:   new ArrayList<>(new LinkedHashSet<>(each sale's item))
                                totals:        TreeMap; for each sale: map.merge(merchant, price, Integer::sum)
                                """),
                        "ICT261 Unit 5 (collections): choosing List/Set/Map by behaviour and performance, "
                                + "equals() and hashCode(), and streams (filter, map, groupingBy, counting).",
                        100),

                room(14, WORLD_3, "w3", "Level14_Leaderboard", "The Leaderboard",
                        """
                        Carved into the Armory wall is the Leaderboard of every adventurer who has made it
                        this far, and its runes have been shuffled by a prankster imp. The scribes need it
                        back in order: most points first, faster runs breaking ties. Then a speedrunners'
                        board, and an alphabetical roll call. One natural order, many other orders.
                        """,
                        "Score.compareTo, ranked, topNames, speedrunOrder and byName. Orders are in the file.",
                        List.of(
                                "compareTo returns negative if THIS comes first, positive if OTHER comes first, "
                                        + "0 if they tie. Compare the most important thing first, and only look "
                                        + "at the next thing on a tie.",
                                "For 'bigger first', swap the arguments: Integer.compare(other.points, points). "
                                        + "Sort a COPY: new ArrayList<>(scores). A Comparator can be built from "
                                        + "pieces: Comparator.comparingInt(Score::seconds).thenComparing(...), "
                                        + "and .reversed() flips one piece. String.CASE_INSENSITIVE_ORDER ignores case.",
                                """
                                compareTo: c = Integer.compare(other.points, points); if c != 0 return c
                                           c = Integer.compare(seconds, other.seconds); if c != 0 return c
                                           return player.compareTo(other.player)
                                ranked:    copy = new ArrayList<>(scores); Collections.sort(copy); return copy
                                topNames:  ranked(scores).stream().limit(n).map(Score::player).toList()
                                """),
                        "Comparable (natural order, compareTo) vs Comparator (other orders), building "
                                + "comparators with comparing / thenComparing / reversed, and sorting a copy. "
                                + "Supports ICT261 Unit 5 (collections).",
                        100),

                room(15, WORLD_3, "w3", "Level15_ShopSystem", "BOSS: The Shop System",
                        """
                        The Armory's shop is run by a golem that cannot count, cannot say no, and gives
                        change in whatever it finds on the floor. The guild wants a real shop system before
                        the next expedition sets out: proper stock, polite refusals that say exactly what
                        went wrong, and nightly reports. A failed sale must leave the shelves untouched.
                        """,
                        "The two exceptions, the generic Shop<P extends Product> and its reports. Rules are in the file.",
                        List.of(
                                "Do every check before changing anything; then a thrown exception automatically "
                                        + "leaves the shop untouched. Pick collections by job: look-up by name "
                                        + "(Map), first-stocked order (LinkedHashMap), a history (List).",
                                "Custom exceptions build their message in super(...). buy() declares 'throws "
                                        + "ShopException', so callers can catch the parent and handle both kinds. "
                                        + "map.merge(name, qty, Integer::sum) handles restocking. Reports: "
                                        + "collect(Collectors.groupingBy(Sale::category, TreeMap::new, "
                                        + "Collectors.summingInt(Sale::total))).",
                                """
                                fields: Map<String, P> products; Map<String, Integer> quantities (LinkedHashMap);
                                        List<Sale> sales
                                buy: qty <= 0 -> throw IAE; available = quantityOf(name)
                                     available < qty -> throw new OutOfStockException(name, qty, available)
                                     total = price * qty; gold < total -> throw new NotEnoughGoldException(total, gold)
                                     only now: reduce stock, record Sale, return gold - total
                                lowStock: quantities entries, filter value < threshold, map key, sorted, toList
                                """),
                        "Everything from ICT261 Units 4 and 5: checked exceptions with a shared parent, a "
                                + "generic class with a bounded type, choosing collections, and stream reports.",
                        250));
    }
}

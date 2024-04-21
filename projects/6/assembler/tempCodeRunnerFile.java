String line1 = read1.readLine();
            while (line1 != null) {
                if (!line1.isEmpty()) {
                    if (line1.charAt(0) != '/') {
                        Parser parser = new Parser(line1);
                        Parser.isFirstPass = false;
                        parser.scanTokens();
                    }
                }
                line1 = read1.readLine();
            }
            read1.close();